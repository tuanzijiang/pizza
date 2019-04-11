import { User } from '@entity/User';
import { Pizza } from '@entity/Pizza';
import { Order } from '@entity/Order';
import { Address } from '@entity/Address';
import { info, LogTag, error } from '@utils/log';

const databaseName = 'pizza';
const version = 1;

export enum OBJECT_STORE_NAMES {
  USER = 'USER',
  ADDRESS = 'ADDRESS',
  ORDER = 'ORDER',
  PIZZA = 'PIZZA',
  COMMON = 'COMMON',
}

const objectStoreNames = {
  [OBJECT_STORE_NAMES.USER]: {
    name: OBJECT_STORE_NAMES.USER,
  },
  [OBJECT_STORE_NAMES.ADDRESS]: {
    name: OBJECT_STORE_NAMES.ADDRESS,
  },
  [OBJECT_STORE_NAMES.ORDER]: {
    name: OBJECT_STORE_NAMES.ORDER,
  },
  [OBJECT_STORE_NAMES.PIZZA]: {
    name: OBJECT_STORE_NAMES.PIZZA,
  },
  [OBJECT_STORE_NAMES.COMMON]: {
    name: OBJECT_STORE_NAMES.COMMON,
  },
};

let request: IDBOpenDBRequest = null;
let db: IDBDatabase = null;

export const open = () => {
  if (!request && !db) {
    request = window.indexedDB.open(databaseName, version);

    request.onsuccess = (e: any) => {
      db = e.target.result;
    };

    request.onupgradeneeded = (e: any) => {
      db = e.target.result;
      Object.values(objectStoreNames).forEach(v => {
        const name = v.name;
        if (db.objectStoreNames.contains(name)) {
          db.deleteObjectStore(name);
        }
        db.createObjectStore(name, { keyPath: 'id', autoIncrement: false });
      });
    };

    request.onerror = (e) => {
      console.error(e);
    };
  }
};

open();

export const close = () => {
  db.close();
  request = null;
  db = null;
};

export const add =
  (name: OBJECT_STORE_NAMES, objs: User[] | Pizza[] | Order[] | Address[] | any) => {
    const transaction = db.transaction([name], 'readwrite');
    const objectStore = transaction.objectStore(name);

    objs.forEach((obj: User | Pizza | Order | Address) => {
      const storeRequest = objectStore.put(obj);
      storeRequest.onsuccess = () => {
        info(LogTag.DB, `${name} success`, obj);
      };
      storeRequest.onerror = (event) => {
        error(LogTag.DB, `${name} error`, event);
      };
    });
  };

export const clear = (name: OBJECT_STORE_NAMES) => {
  open();
  const transaction = db.transaction([name], 'readwrite');
  const objectStore = transaction.objectStore(name);

  objectStore.clear();
};

export const fetch =
  (name: OBJECT_STORE_NAMES, fn?: (obj: any) => any, fnEnd?: () => any) => {
    const transaction = db.transaction([name], 'readwrite');
    const objectStore = transaction.objectStore(name);

    objectStore.openCursor().onsuccess = (event: any) => {
      const cursor = event.target.result;
      if (cursor) {
        if (fn) {
          fn(cursor.value);
        }
        cursor.continue();
      } else {
        if (fnEnd) {
          fnEnd();
        }
      }
    };
  };

export enum TIMEFOAMTER {
  TYPE1 = 'TYPE1',
  TYPE2 = 'TYPE2',
}

const getFormater = (type: TIMEFOAMTER) => {
  switch (type) {
    case TIMEFOAMTER.TYPE1: {
      return ({
        year, month, day, hour, minutes, second,
      }: {
        year: string, month: string, day: string, hour: string, minutes: string, second: string,
      }) => (
          `${year}/${month}/${day} ${hour}:${minutes}:${second}`
        );
    }
    case TIMEFOAMTER.TYPE2: {
      return ({
        year, month, day, hour, minutes, second,
      }: {
        year: string, month: string, day: string, hour: string, minutes: string, second: string,
      }) => (
          `${year}-${month}-${day}`
        );
    }
    default: {
      return null;
    }
  }
};

export const formater = (timeNumber: number, kind: TIMEFOAMTER) => {
  const time = new Date(timeNumber - 0);
  const year = time.getFullYear().toString();
  let month = (time.getMonth() + 1).toString();
  if (month.length === 1) {
    month = `0${month}`;
  }
  let day = time.getDate().toString();
  if (day.length === 1) {
    day = `0${day}`;
  }
  let hour = time.getHours().toString();
  if (hour.length === 1) {
    hour = `0${hour}`;
  }
  let minutes = time.getMinutes().toString();
  if (minutes.length === 1) {
    minutes = `0${minutes}`;
  }
  let second = time.getSeconds().toString();
  if (second.length === 1) {
    second = `0${second}`;
  }
  const currFormater = getFormater(kind);
  if (currFormater) {
    return currFormater({
      year, month, day, hour, minutes, second,
    });
  }
};

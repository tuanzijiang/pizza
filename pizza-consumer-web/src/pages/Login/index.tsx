import * as React from 'react';
import Banner from '@biz-components/Banner';
import './index.scss';

interface LoginProps { }

interface LoginState { }

export default class Login extends React.PureComponent<LoginProps, LoginState> {
  render() {
    return (
      <div className="login">
        <Banner right={null} leftClick={() => { console.warn(1111); }}/>
        <div className="login-list">
          <div>1</div>
          <div>2</div>
          <div>3</div>
          <div>4</div>
          <div>5</div>
          <div>6</div>
          <div>7</div>
          <div>8</div>
          <div>1</div>
          <div>1</div>
          <div>1</div>
          <div>1</div>
          <div>8</div>
          <div>6</div>
          <div>4</div>
          <div>3</div>
          <div>2</div>
          <div>1</div>
          <div>2</div>
          <div>3</div>
          <div>4</div>
          <div>5</div>
          <div>6</div>
          <div>7</div>
          <div>8</div>
          <div>8</div>
          <div>6</div>
          <div>4</div>
          <div>3</div>
          <div>2</div>
          <div>1</div>
          <div>2</div>
          <div>3</div>
          <div>4</div>
          <div>5</div>
          <div>6</div>
          <div>7</div>
          <div>8</div>
          <div>8</div>
          <div>6</div>
          <div>4</div>
          <div>3</div>
          <div>2</div>
          <div>1</div>
          <div>2</div>
          <div>3</div>
          <div>4</div>
          <div>5</div>
          <div>6</div>
          <div>7</div>
          <div>8</div>
          <div>8</div>
          <div>6</div>
          <div>4</div>
          <div>3</div>
          <div>2</div>
          <div>1</div>
          <div>2</div>
          <div>3</div>
          <div>4</div>
          <div>5</div>
          <div>6</div>
          <div>7</div>
          <div>8</div>
          <div>8</div>
          <div>6</div>
          <div>4</div>
          <div>3</div>
          <div>2</div>
          <div>1</div>
          <div>2</div>
          <div>3</div>
          <div>4</div>
          <div>5</div>
          <div>6</div>
          <div>7</div>
          <div>8</div>
        </div>
      </div>
    );
  }
}

// export default connect((state) => {
//   return {
//     page: state.page.edit,
//     entity: state.entity,
//   };
// }, (dispatch) => {
//   return {
//     updateNavIdx: (idx: number) => { dispatch(pageActionCreator.edit.updateNavIdx(idx)); },
//     updateDisplayId: (displayId: string) => {
//       dispatch(entityActionCreator.display.updateDisplayId(displayId));
//     },
//   };
// })(Edit);

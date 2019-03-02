import * as React from 'react';
import './index.scss';

interface LoginProps { }

interface LoginState { }

export default class Login extends React.PureComponent<LoginProps, LoginState> {
  render() {
    return (
      <div className="Login">
        <span className="Login1">login1</span>
        <span className="Login2">login2</span>
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

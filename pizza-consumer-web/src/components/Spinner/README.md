---
title: Spinner
description: Spinner是一个蓝色的旋转圈圈，用来显示正在加载的情形。
---

# Spinner

![Spinner.gif](../images/spinner.gif)

Spinner是一个蓝色的旋转圈圈，用来显示正在加载的情形。（因为Gif录制工具问题，实际旋转速度是均匀的）

## API

Spinner组件接受一个`style`属性对象，该对象可以设置Spinner的宽度和高度，单位是像素。默认宽高为`16px`。

## 示例代码

```javascript
import Spinner from '@byted/lark-js-component/lib/spinner';

function render() {
  return (
    <Spinner style={{ width: 16, height: 16 }} />
  );
}
```

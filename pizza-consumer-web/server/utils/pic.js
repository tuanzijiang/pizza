const _ = require('lodash');
const pizzaPics = [
  'http://p0.meituan.net/bbia/29b7018a2f773561d63d95f420c3e5e01138544.jpg%40249w_249h_1e_1c_1l%7Cwatermark%3D1%26%26r%3D1%26p%3D9%26x%3D2%26y%3D2%26relative%3D1%26o%3D20',
  'http://p0.meituan.net/bbia/50f0f9901b7699bd0a7eb6edb1a34da21348086.jpg%40249w_249h_1e_1c_1l%7Cwatermark%3D1%26%26r%3D1%26p%3D9%26x%3D2%26y%3D2%26relative%3D1%26o%3D20',
  'http://p0.meituan.net/wmproduct/52e14f285e4c697a98b14836a0229f8f715661.jpg%40249w_249h_1e_1c_1l%7Cwatermark%3D1%26%26r%3D1%26p%3D9%26x%3D2%26y%3D2%26relative%3D1%26o%3D20',
  'http://p0.meituan.net/wmproduct/1f2843faf145dcab4588ddbab9ab7abe292658.jpg%40249w_249h_1e_1c_1l%7Cwatermark%3D1%26%26r%3D1%26p%3D9%26x%3D2%26y%3D2%26relative%3D1%26o%3D20',
  'http://p1.meituan.net/wmproduct/b9591f2d5d80ff922ced36a86129d5dc412004.jpg%40249w_249h_1e_1c_1l%7Cwatermark%3D1%26%26r%3D1%26p%3D9%26x%3D2%26y%3D2%26relative%3D1%26o%3D20',
]

const getPizzaPics = () => {
  return pizzaPics[_.random(0, pizzaPics.length - 1)];
};

module.exports = {
  getPizzaPics,
};
const getDOMToken = (dom, needCount) => {
  const tagName = dom.tagName;
  const tagId = dom.id ? `#${dom.id}` : '';
  const tagClass = Array.prototype.map.apply(
    dom.classList, [v => `.${v}`]
  ).reduce((prev, curr) => (`${prev}${curr}`), '');
  const tokenWithoutCount = `${tagName}${tagId}${tagClass}`;

  if (needCount) {
    let i = 1;
    let brotherDom = dom.previousElementSibling;
    while (brotherDom) {
      i++;
      brotherDom = brotherDom.previousElementSibling;
    }
    return `${tokenWithoutCount}:nth-child(${i})`;
  }
  return tokenWithoutCount;
};

const fetchSelector = (dom) => {
  const domTokenPath = [];
  let currDom = dom;
  while (currDom !== document) {
    const token = getDOMToken(currDom, true);
    domTokenPath.unshift(token);
    currDom = currDom.parentNode;
  }
  return domTokenPath.join('>');
}

module.exports = {
  getDOMToken,
  fetchSelector,
}

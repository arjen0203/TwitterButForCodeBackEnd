var Filter = require('bad-words');
const filter = new Filter();

module.exports = async function (context, req) {
     const text = (req.body);
    if (req.body) {
        context.res = {
            status: 200,
            body: filter.clean(text)
        };
    } else {
        context.res = {
            status: 406,
            body: "no body string was given"
        };
    }
}
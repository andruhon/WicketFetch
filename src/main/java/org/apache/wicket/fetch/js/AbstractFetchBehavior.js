export default class AbstractFetchBehavior {

    constructor() {
        console.log("AbstractFetchBehavior")
    }

    registerCallback(name, callback) {
        AbstractFetchBehavior.registeredCallbacks[name] = callback;
    }

    bind() {
        return new Promise(resolve => {
            document.addEventListener('readystatechange', event => {
                if (event.target.readyState === 'interactive') {
                    console.log("loading");
                }
                else if (event.target.readyState === 'complete') {
                    console.log("ready");
                    resolve();
                }
            });
        });
    }

}
AbstractFetchBehavior.registeredCallbacks = {};
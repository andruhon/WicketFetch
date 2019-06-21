import AbstractFetchBehavior from "./AbstractFetchBehavior.js";

export default class OnChangeFetchBehavior extends AbstractFetchBehavior{

    /**
     *
     * @param {string} id
     * @param {string} callbackUrl
     */
    constructor(id, callbackUrl) {
        super();
        this.registerCallback(this.callback);
        this.bind().then(value => {
            document.getElementById(id).addEventListener("input", function (ev) {
                /**
                 * @type {HTMLInputElement}
                 */
                const target = ev.target;
                // let data = {value: target.value};
                // TODO use json payload
                let data = new FormData();
                data.append("field2",Date.now().toString());;
                let myRequest = new Request(callbackUrl, {
                    method: 'POST',
                    body: data,
                    cache: 'no-cache',
                    headers: {
                        // 'Content-Type': 'application/json',
                        "Wicket-Fetch": true
                    }
                });
                fetch(myRequest)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('HTTP error, status = ' + response.status);
                    }
                    console.log(response);
                    return response.json();
                }).then(value1 => {
                    console.log(value1);
                })

            })
        })
    }

    callback () {

    }


}
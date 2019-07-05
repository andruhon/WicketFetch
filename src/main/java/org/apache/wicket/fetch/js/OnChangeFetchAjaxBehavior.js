import AbstractFetchBehavior from "./AbstractFetchBehavior.js";

export default class OnChangeFetchAjaxBehavior extends AbstractFetchBehavior {

    /**
     *
     * @param {string} id
     * @param {string} callbackUrl
     */
    constructor(id, callbackUrl) {
        super();
        this.registerCallback("OnChangeFetchAjaxBehavior.callback", this.callback);
        this.bind().then(value => {
            document.getElementById(id).addEventListener("input", function (ev) {
                let data = new FormData();
                data.append(ev.target.name, ev.target.value);
                let myRequest = new Request(callbackUrl, {
                    method: 'POST',
                    body: data,
                    cache: 'no-cache',
                    headers: {
                        "Wicket-Fetch": true,
                        "Wicket-Ajax": true,
                        // TODO
                        "Wicket-Ajax-BaseURL": WicketFetchBaseUrl
                    }
                });
                fetch(myRequest)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('HTTP error, status = ' + response.status);
                        }
                        console.log(response);
                        return response.text();
                    })
                    .then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
                    .then(data => {
                        let ajaxResponse = data.getElementsByTagName("ajax-response")[0];
                        for (let c = 0; c < ajaxResponse.childNodes.length; ++c) {
                            let node = ajaxResponse.childNodes[c];

                            if (node.tagName === "component") {
                                // TODO component
                            } else if (node.tagName === "evaluate") {
                                // This code simplu uses existing wicket-ajax backend, so just strip function wrapper, and take json
                                const jsonString = node.textContent.replace(/^\(function\(\)\{/, "").replace(/\}\)\(\);/, "")
                                const data = JSON.parse(jsonString);
                                /**
                                 * @type {Function}
                                 */
                                const callback = AbstractFetchBehavior.registeredCallbacks[data.callback];
                                callback.apply(this, data.arguments);
                                console.log(data);
                            } else if (node.tagName === "redirect") {
                                // TODO redirect
                            }

                        }
                    })

            })
        })
    }

    callback(a, b) {
        console.log(a, b);
    }

}
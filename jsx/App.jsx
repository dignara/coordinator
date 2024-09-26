import React from "react"
import ReactDOM from "react-dom/client"
import {Provider} from "react-redux"

import store from "./modules/store"

import RouterContainer from "./components/router/RouterContainer"

const routerElement = document.getElementById('container')
if (routerElement !== null) {
    const routerRoot = ReactDOM.createRoot(routerElement)
    routerRoot.render(<Provider store={store}><RouterContainer/></Provider>)
}

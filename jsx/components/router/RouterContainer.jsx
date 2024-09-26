import React, {Suspense, lazy} from "react"
import {
    BrowserRouter as Router,
    Route,
    Routes
} from 'react-router-dom'
import Loading from "../main/Loading"

const WrapContents = lazy(() => import("../main/WrapContents"))

const RouterContainer = () => {
    return (
        <Router>
            <Suspense fallback={<Loading/>}>
                <Routes>
                    <Route path='/' element={<WrapContents/>}/>
                </Routes>
            </Suspense>
        </Router>
    )
}

export default RouterContainer
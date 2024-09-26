import React from "react"
import {showGoodsModal, showLoginModal} from "../../modules/modalSlice"
import {useDispatch, useSelector} from "react-redux";
import {isAdmin} from "../../modules/statusSlice"

const Header = () => {
    const dispatch = useDispatch()
    const admin = useSelector(isAdmin)
    return (
        <header>
            <section>
                <div className="d-flex">
                    <div className="flex-grow-1">
                        <img
                            src="https://image.msscdn.net/display/images/2024/07/19/3a7caf3364184181a3cae5741f91464f.png"
                            alt="musinsaLogo"/>
                    </div>
                    {
                        admin && <a className="me-2" onClick={() => dispatch(showGoodsModal())}><i className="bi bi-bag-plus"></i></a>
                    }
                    <a className="me-2" onClick={() => dispatch(showLoginModal())}><i className={`bi ${admin ? 'bi-person-gear' : 'bi-person'}`}></i></a>
                </div>
            </section>
        </header>
    )
}

export default Header
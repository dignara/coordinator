import React from "react"
import {Modal} from 'react-bootstrap'
import {useDispatch, useSelector} from "react-redux"
import {hideLoginModal} from "../../modules/modalSlice"
import {isAdmin, setAdmin} from "../../modules/statusSlice"
import {toast} from "react-hot-toast"

const LoginModal = () => {
    const dispatch = useDispatch()
    const admin = useSelector(isAdmin)

    const closeModal = () => {
        dispatch(hideLoginModal())
    }

    const onSetAdmin = (isAdmin) => {
        toast.success(isAdmin ? "운영자님 반갑습니다." : "고객님 반갑습니다.")
        dispatch(setAdmin(isAdmin))
        closeModal()
    }

    return (
        <>
            <Modal show={true} onHide={closeModal} backdrop='static' centered>
                <Modal.Header data-bs-theme="dark" closeButton>
                    <Modal.Title>
                        사용자 선택
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="row px-2">
                        <button type="button" className="col btn btn-dark user me-1" disabled={!admin} onClick={() => onSetAdmin(false)}>
                            <i className="bi bi-person user-icon"/>
                            <span className="user-title">고객</span>
                        </button>
                        <button type="button" className="col btn btn-dark user ms-1" disabled={admin} onClick={() => onSetAdmin(true)}>
                            <i className="bi bi-person-gear user-icon"/>
                            <span className="user-title">운영자</span>
                        </button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default LoginModal
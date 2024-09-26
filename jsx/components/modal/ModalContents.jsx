import React from "react"

import {useSelector} from "react-redux";
import {getGoodsModal, getLoginModal} from "../../modules/modalSlice";
import GoodsModal from "./GoodsModal";
import LoginModal from "./LoginModal";

const ModalContents = () => {
    const goodsModal = useSelector(getGoodsModal)
    const loginModal = useSelector(getLoginModal)
    return (
        <>
            {
                goodsModal.show && <GoodsModal goods={goodsModal.goods} />
            }
            {
                loginModal.show && <LoginModal/>
            }
        </>
    )
}

export default ModalContents
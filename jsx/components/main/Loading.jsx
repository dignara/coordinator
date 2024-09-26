import React from "react"
import ReactTextRotator from "react-text-rotator"

const contentTexts = [
    '우리는 한 팀으로 움직여',
    '압도적인 결과를 만들어갑니다.'
]

const content = contentTexts.map(text => ({text: text, className: 'text-center text-secondary', animation: 'fade'}))

const Loading = () => {
    return (
        <>
            <div className="position-absolute top-50 start-50 translate-middle" data-aos="fade-up">
                <img src='/img/logo.png' alt='musinsa' style={{width: '200px'}}/>
            </div>
            <div className="position-absolute top-50 start-50 translate-middle mt-5" data-aos="fade-up">
                <ReactTextRotator content={content} startDelay={100}/>
            </div>
        </>
    )
}

export default Loading
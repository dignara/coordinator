import React, {useState} from "react"
import {Tab, TabList, TabPanel, Tabs} from 'react-tabs'
import LowPriceGoodsByCategory from "../article/LowPriceGoodsByCategory";
import LowPriceBrandGoods from "../article/LowPriceBrandGoods";
import CategoryHighAndLowPriceGoods from "../article/CategoryHighAndLowPriceGoods";

const TABS = [
    {title: '구현1', component: <LowPriceGoodsByCategory/>},
    {title: '구현2', component: <LowPriceBrandGoods/>},
    {title: '구현3', component: <CategoryHighAndLowPriceGoods/>}
]

const Main = () => {
    const [tabIndex, setTabIndex] = useState(0)
    let tabs = []
    let panels = []
    TABS.forEach((tab, index) => {
            tabs.push(<Tab><a className={`${tabIndex === index ? 'active' : ''}`}><span>{tab.title}</span></a></Tab>)
            panels.push(<TabPanel>{tab.component}</TabPanel>)
        }
    )
    return (
        <Tabs onSelect={index => setTabIndex(index)}>
            <TabList className='react-tabs__tab-list d-flex justify-content-start ps-2'>
                {tabs}
            </TabList>
            <article>
                {panels}
            </article>
        </Tabs>
    )
}

export default Main
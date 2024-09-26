import React from "react"
import {ApolloClient, ApolloProvider, InMemoryCache} from '@apollo/client'
import {Toaster} from "react-hot-toast"
import Header from "./Header"
import Main from "./Main"
import ModalContents from "../modal/ModalContents";
import {ArticleProvider} from "../provider/ArticleContext";

const client = new ApolloClient({
    uri  : '/graphql',
    cache: new InMemoryCache(),
});

const WrapContents = () => {
    return (
        <ApolloProvider client={client}>
            <ArticleProvider>
                <ModalContents/>
                <Header/>
                <Main/>
                <Toaster position="top-center" reverseOrder={false}/>
            </ArticleProvider>
        </ApolloProvider>
    )
}
export default WrapContents
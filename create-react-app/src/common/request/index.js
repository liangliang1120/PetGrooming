// encapsulated axios
import axios from 'axios'
import { BASE_URL, TIMEOUT } from './requestConfig'

// not ajax => axios
const instance = axios.create({
    baseURL: BASE_URL,
    timeout: TIMEOUT
})

// Request interceptor Some operations before initiating an http request
// 1、Before sending the request, load some components
// 2、Some requests need to carry the token. If it is said that it is not carried, it will directly jump to the login page.
instance.interceptors.request.use((config) => {
    console.log('Some operations that were intercepted')
    return config
}, err => {
    return err
})

// response interceptor
instance.interceptors.response.use((res) => {
    return res
}, err => {
    if (err && err.response) {
        switch(err.response.status) {
            case 400:
                console.log('Request Error')
                break
            case 401:
                console.log('Not Certified')
                break
            default:
                console.log('Miscellaneous Information')
        }
    }
})

export default instance

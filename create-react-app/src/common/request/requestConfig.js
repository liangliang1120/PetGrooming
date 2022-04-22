// The configuration file of axios, you can distinguish some global configurations 
// such as development environment and production environment here
const devBaseUrl = '/'
const proBaseUrl = '/'

// process.env : return an environment information containing the user, 
// which can distinguish whether it is a development environment or a production environment
console.log(process.env)
export const BASE_URL =  process.env.NODE_ENV === 'development' ? devBaseUrl : proBaseUrl

export const TIMEOUT = 5000
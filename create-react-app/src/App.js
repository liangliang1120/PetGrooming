import * as React from 'react';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import topImg from './img/head_dog 584_300.png';
import contentImg from './img/grooming689_398.jpg'
import {Button, TextField} from "@mui/material";
import {useEffect, useState} from "react";
import AddCircleIcon from '@mui/icons-material/AddCircle';
import PetTable from "./components/PetTable";
import GroomTable from "./components/GroomTable";
import BathTable from "./components/BathTable";
import bath1dog from "./img/bath1dog.jpeg";
import groom1dog from "./img/groom1dog.jpg";
import cuteDog from './img/Schnauzer-pink.jpg';
import LogoutIcon from '@mui/icons-material/Logout';
import LoginIcon from '@mui/icons-material/Login';
import {get} from "lodash";
import axios from 'axios';
import moment from "moment";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import {Form, message, Spin} from 'antd';
import { useCookieState } from 'ahooks';
import DialogActions from "@mui/material/DialogActions";
import './App.less';

const columns = [
    { field: 'Name', headerName: 'Name', width: 70 },
    { field: 'Grade', headerName: 'Grade', width: 130 },
    { field: 'Date', headerName: 'Date', width: 130 },
];
const rows = [
    { id:1, Name: 'Snow', Grade: 'Jon', Date: 35 },
    { id:2, Name: 'Lannister', Grade: 'Cersei', Date: 42 },
    { id:3, Name: 'Lannister', Grade: 'Jaime', Date: 45 },
    { id:4, Name: 'Stark', Grade: 'Arya', Date: 16 },
    { id:5, Name: 'Targaryen', Grade: 'Daenerys', Date: null },
    { id:6, Name: 'Melisandre', Grade: null, Date: 150 },
    { id:7, Name: 'Clifford', Grade: 'Ferrara', Date: 44 },
    { id:8, Name: 'Frances', Grade: 'Rossini', Date: 36 },
    { id:9, Name: 'Roxie', Grade: 'Harvey', Date: 65 },
];
const statusData = {
    login: 'login',
    pet: 'pet',
    grooming: 'grooming',
    bath: 'bath',
}

export default function App() {
  // status 1 : login page
  // status 2 : petManage page
  const [status, setStatus] = useState(statusData.login);
  const [petData, setPetData] = useState([]);
  const [bathData, setBathData] = useState([]);
  const [groomData, setGroomData] = useState([]);
  const [scheduleData, setScheduleData] = useState([]);
  const [customerData, setCustomerData] = useState([]);
  const [employData, setEmployData] = useState([]);
  const [openAddPet, setOpenAddPet] = React.useState(false);
  const [value, setValue] = React.useState({});
  const [open, setOpen] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const [formGroom] = Form.useForm();
  const [formBath] = Form.useForm();
  // const [loginUser, setLoginUser] = React.useState(false);
  let [loginUser, setLoginUser] = useCookieState('useCookieStateString');
  if(loginUser&&status===statusData.login){
      setStatus(statusData.pet)
      setLoginUser(JSON.parse(loginUser));
  }
  const statusCSSMap = {
      [statusData.login]: {background:'#556cd6'},
      [statusData.grooming]: {background:'#556cd6',height:'calc( 100% - 230px)'},
      [statusData.pet]: {background:'#556cd6',textAlign: 'center',padding: 20},
      [statusData.bath]: {background:'#556cd6',height:'calc( 100% - 230px)'},
  }
  const getPetList = async ()=>{
      const result = await axios.get('/pets');
      if(get(result,'status')===200){
          result.data = result.data.filter(item=>item.cusId===get(loginUser,'cusId'))
          setPetData(get(result,'data',[]))
      }
  }
  const getBathList = async ()=>{
    const result = await axios.get('/books');
    if(get(result,'status')===200){
        // result.data = result.data.filter(item=>moment(item.bookTime).format('YYYY-MM-DD')===moment().format('YYYY-MM-DD'))
        // result.data = result.data.filter(item=>item.serviceKind===1)
        setBathData(get(result,'data',[]))
    }
  }
  const getGroomList = async ()=>{
        const result = await axios.get('/books');
        if(get(result,'status')===200){
            // result.data = result.data.filter(item=>moment(item.bookTime).format('YYYY-MM-DD')===moment().format('YYYY-MM-DD'))
            // result.data = result.data.filter(item=>item.serviceKind===0)
            setGroomData(get(result,'data',[]))
        }
  }
  const getSchemeList = async ()=>{
        const result = await axios.get('/schedules/employee');
        if(get(result,'status')===200){
            result.data = result.data.filter(item=>moment(item.workTime).isAfter(moment()))
            for(let item of result.data||[]){
                item.endTime = moment(item.workTime).add('hour',1).format('MM-DD HH:mm:ss')
                item.workTime = moment(item.workTime).format('MM-DD HH:mm:ss');
            }
            result.data.sort((a,b)=>{
                return parseInt(a.workTime)>parseInt(b.workTime)
            })
            setScheduleData(get(result,'data',[]))
        }
  }
  const getEmployeeList = async ()=>{
        const result = await axios.get('/employees');
        if(get(result,'status')===200){
            setEmployData(get(result,'data',[]))
        }
  }
    const getCustomerList = async ()=>{
        const result = await axios.get('/customers');
        if(get(result,'status')===200){
            setCustomerData(get(result,'data',[]))
        }
    }
  useEffect(()=>{
      getEmployeeList();
      getCustomerList();
      getPetList();
  },[])
  useEffect(()=>{
      if(status===statusData.pet){
          getPetList();
      }else if(status===statusData.bath){
          getSchemeList();
          getBathList();
      }else if(status===statusData.grooming){
          getSchemeList();
          getGroomList();
      }
  }, [status]);
  const handleLogin=()=>{
      const userObj = {
          'admin':{
              isAdmin: true,
              cusId: 0,
              cusName: 'Admin'
          },
      };
      for(let item of customerData){
          userObj[item.cusName] = item;
      }
      if(!userObj[value.userName]||value.password!=='123456'){
          message.error("the userName or password is wrong, check it!");
      }else{
          setLoginUser(userObj[value.userName]);
          setOpen(false)
          setStatus(statusData.pet);
          setValue({});
          message.success("login successful!");
      }
  };
  const loginOut = ()=>{
      setLoginUser(null);
      setStatus(statusData.login);
  }
  return (
   <Spin tip="Loading..." spinning={loading}>
    <div style={{width:1400,margin:'auto',height: '100%'}}>
        <Dialog open={open} onClose={()=>setOpen(false)}>
            <DialogTitle>Login In</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    please input your info to login in.
                </DialogContentText>
                <TextField
                    autoFocus
                    margin="dense"
                    id="userName"
                    label="userName"
                    fullWidth
                    variant="standard"
                    onChange={(e) => {
                        setValue((value)=>({...value,userName:e.target.value}));
                    }}
                >
                </TextField>
                <TextField
                    autoFocus
                    margin="dense"
                    id="password"
                    label="password"
                    fullWidth
                    type="password"
                    variant="standard"
                    onChange={(e) => {
                            setValue((value) => ({...value, password: e.target.value}));
                    }}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={()=>setOpen(false)}>Cancel</Button>
                <Button onClick={handleLogin}>Submit</Button>
            </DialogActions>
        </Dialog>
        <div className='header'>
            <div className='header-top'>
                <img src={topImg} height={230}/>
                <span className='right-title'>
                    Leon's Pet Grooming
                </span>
                <span className='right-content'>
                    {loginUser?<span onClick={loginOut}>
                        <span>welcome {loginUser.cusName}
                             , loginOut</span><LogoutIcon/>
                        </span>:
                        <span onClick={()=>setOpen(true)}>
                            <span>Login In</span><LoginIcon/>
                        </span>}
                </span>
            </div>
        </div>
        <div className='content' style={statusCSSMap[status]}>
            {status===statusData.login&&<>
            <img src={contentImg} height='100%' style={{paddingLeft:150,maxWidth:'60%',height:400}} align='right'/>
            <span className='word'>
                At leon's Pet Grooming Sport, we
                thrive on the ability to get to know
                our clients and customize their grooming
                experience to create a safer and more
                enjoyable grooming experience. We hope
                to see you and your furry friend(s) soon.
           </span></>}
            {status===statusData.pet&&<>
                     <Typography
                         sx={{ flex: '1 1 100%' }}
                         variant="h6"
                         id="tableTitle"
                         component="div"
                         style={{color:'#fff'}}
                         >
                      Your Pet Information
                    </Typography>
                    <span className='pet-table'>
                        <PetTable
                            setLoading={setLoading}
                            data={petData}
                            reload={getPetList}
                            openAdd={openAddPet}
                            setOpenAdd={setOpenAddPet}
                            loginUser={loginUser}
                        />
                    </span>
                    <div className='add'>
                        <Button onClick={()=>setOpenAddPet(true)} style={{width:140}} variant="contained"  color="error">
                            <AddCircleIcon style={{marginRight:3}}/> add pet
                        </Button>
                    </div>
            </>}
            {status===statusData.grooming&&<div className='table' style={{display:'flex',height:'100%'}}>
                <div style={{display:'inline-block',width:'75%',textAlign:'center'}}>
                    <div style={{marginTop:30}}>
                        <Typography
                            sx={{ flex: '1 1 100%' }}
                            variant="h5"
                            id="tableTitle"
                            component="div"
                            style={{color:'#fff'}}
                        >
                            GROOM BOOKING
                        </Typography>
                        <span className='pet-table'>
                            <GroomTable
                                form={formGroom}
                                setLoading={setLoading}
                                data={scheduleData}
                                bookData={groomData}
                                reload={()=>{getSchemeList();getGroomList();}}
                                openAdd={openAddPet}
                                setOpenAdd={setOpenAddPet}
                                employData={employData}
                                petData={petData}
                                loginUser={loginUser}
                            />
                        </span>

                        <div className='add'>
                            {get(loginUser,'isAdmin')&&<Button onClick={()=>{
                                formGroom.resetFields();
                                setOpenAddPet(true);
                            }} style={{width:280,height:50,fontSize:20,backgroundColor:'rgb(219, 14, 79)'}} variant="contained"  >
                                Add Schedule<img src={cuteDog} width={50} height={50}/>
                            </Button>}
                        </div>
                    </div>
                </div>
                <div style={{display:'inline-block',width:'25%',borderLeft: '1px solid #cecece',height:'100%'}}>
                    <img src={groom1dog} style={{width:'100%'}} />
                    <div style={{width:'60%',margin:'auto',marginTop:20}}>
                        <div className='desc-title'>GROOMING</div>
                        <div className='desc-content'>SMALL (1-19 LBS)</div>
                        <div className='desc-content-price'>$80-$95</div>

                        <div className='desc-content'>MEDIUM (20-50 LBS)</div>
                        <div className='desc-content-price'>$95-$115</div>

                        <div className='desc-content'>LARGE (51+ LBS)</div>
                        <div className='desc-content-price'>$115+</div>
                        <Button onClick={()=>{setStatus(statusData.bath)}} style={{width:220,backgroundColor:'rgb(219, 14, 79)',outlineColor:'white'}} variant="contained"  >
                            BATH BOOKING<img src={cuteDog} width={40} height={40}/>
                        </Button>
                        <Button onClick={()=>{setStatus(statusData.pet)}} style={{width:220,marginTop:10,backgroundColor:'rgb(219, 14, 79)'}} variant="contained"  >
                            PET EDIT<img src={cuteDog} width={40} height={40}/>
                        </Button>
                        <p></p>
                    </div>
                </div>
            </div>}
            {status===statusData.bath&&<div className='table' style={{display:'flex',height:'100%'}}>
                <div style={{display:'inline-block',width:'75%',textAlign:'center'}}>
                    <div style={{marginTop:30}}>
                        <Typography
                            sx={{ flex: '1 1 100%' }}
                            variant="h5"
                            id="tableTitle"
                            component="div"
                            style={{color:'#fff'}}
                        >
                            BATH BOOKING
                        </Typography>
                        <span className='pet-table'>
                            <BathTable
                                form={formBath}
                                setLoading={setLoading}
                                bookData={bathData}
                                data={scheduleData}
                                reload={()=>{getSchemeList();getBathList();}}
                                openAdd={openAddPet}
                                setOpenAdd={setOpenAddPet}
                                employData={employData}
                                petData={petData}
                                loginUser={loginUser}
                            />
                        </span>
                        <div className='add'>
                            {get(loginUser,'isAdmin')&&<Button onClick={()=>{
                                formBath.resetFields();
                                setOpenAddPet(true)
                            }} style={{width:280,height:50,fontSize:20,backgroundColor:'rgb(219, 14, 79)'}} variant="contained"  >
                            Add Schedule<img src={cuteDog} width={50} height={50}/>
                            </Button>}
                        </div>
                    </div>
                </div>
                <div style={{display:'inline-block',width:'25%',borderLeft: '1px solid #cecece',height:'100%'}}>
                    <img src={bath1dog} style={{width:'100%'}} />
                    <div style={{width:'60%',margin:'auto',marginTop:20}}>
                        <div className='desc-title'>BATH</div>
                        <div className='desc-content'>SMALL (1-19 LBS)</div>
                        <div className='desc-content-price'>$50-$65</div>

                        <div className='desc-content'>MEDIUM (20-50 LBS)</div>
                        <div className='desc-content-price'>$65-$85</div>

                        <div className='desc-content'>LARGE (51+ LBS)</div>
                        <div className='desc-content-price'>$85+</div>
                        <Button onClick={()=>{setStatus(statusData.grooming)}} style={{width:220,backgroundColor:'rgb(219, 14, 79)'}} variant="contained"  >
                            GROOMING BOOKING<img src={cuteDog} width={40} height={40}/>
                        </Button>
                        <Button onClick={()=>{setStatus(statusData.pet)}} style={{width:220,marginTop:10,backgroundColor:'rgb(219, 14, 79)'}} variant="contained"  >
                            PET EDIT<img src={cuteDog} width={40} height={40}/>
                        </Button>
                        <p></p>
                    </div>
                </div>
            </div>}
        </div>
        {(status===statusData.login||
        status===statusData.pet) &&<div className='footer'>
            {status===statusData.login&& <Button onClick={()=>setOpen(true)} variant="contained" style={{width:200,height:60,fontSize:28}}>Log In</Button>}
            {status===statusData.pet&& <div className='pet-action'>
                <Button onClick={()=>setStatus(statusData.bath)} variant="contained" style={{width:280,height:60,fontSize:28}}>Book Bathing</Button>
                <Button onClick={()=>setStatus(statusData.grooming)} variant="contained" style={{width:280,height:60,fontSize:28}}>Book Grooming</Button>
            </div>}
        </div>}
    </div>
   </Spin>
  );
}

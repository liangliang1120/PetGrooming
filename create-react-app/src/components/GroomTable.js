import * as React from 'react';
import { styled } from '@mui/system';
import TablePaginationUnstyled from '@mui/base/TablePaginationUnstyled';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {Button, TextField} from "@mui/material";
import {Form, message, Select, DatePicker} from 'antd';
import MenuItem from '@mui/material/MenuItem';
import TimePicker from '@mui/lab/TimePicker';
import axios from 'axios';
import moment from 'moment';
import {cloneDeep, get} from 'lodash';

const blue = {
    200: '#A5D8FF',
    400: '#3399FF',
};

const grey = {
    50: '#F3F6F9',
    100: '#E7EBF0',
    200: '#E0E3E7',
    300: '#CDD2D7',
    400: '#B2BAC2',
    500: '#A0AAB4',
    600: '#6F7E8C',
    700: '#3E5060',
    800: '#2D3843',
    900: '#1A2027',
};

const Root = styled('div')(
    ({ theme }) => `
  table {
    font-family: IBM Plex Sans, sans-serif;
    font-size: 0.875rem;
    border-collapse: collapse;
    width: 100%;
  }

  td,
  th {
    border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
    text-align: left;
    padding: 6px;
  }

  th {
    background-color: ${theme.palette.mode === 'dark' ? grey[900] : grey[100]};
  }
  `,
);

const CustomTablePagination = styled(TablePaginationUnstyled)(
    ({ theme }) => `
  & .MuiTablePaginationUnstyled-spacer {
    display: none;
  }
  & .MuiTablePaginationUnstyled-toolbar {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;

    @media (min-width: 768px) {
      flex-direction: row;
      align-items: center;
    }
  }
  & .MuiTablePaginationUnstyled-selectLabel {
    margin: 0;
  }
  & .MuiTablePaginationUnstyled-select {
    padding: 2px;
    border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
    border-radius: 50px;
    background-color: transparent;
    &:hover {
      background-color: ${theme.palette.mode === 'dark' ? grey[800] : grey[50]};
    }
    &:focus {
      outline: 1px solid ${theme.palette.mode === 'dark' ? blue[400] : blue[200]};
    }
  }
  & .MuiTablePaginationUnstyled-displayedRows {
    margin: 0;

    @media (min-width: 768px) {
      margin-left: auto;
    }
  }
  & .MuiTablePaginationUnstyled-actions {
    padding: 2px;
    border: 1px solid ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
    border-radius: 50px;
    text-align: center;
  }
  & .MuiTablePaginationUnstyled-actions > button {
    margin: 0 8px;
    border: transparent;
    border-radius: 2px;
    background-color: transparent;
    &:hover {
      background-color: ${theme.palette.mode === 'dark' ? grey[800] : grey[50]};
    }
    &:focus {
      outline: 1px solid ${theme.palette.mode === 'dark' ? blue[400] : blue[200]};
    }
  }
  `,
);

export default function UnstyledTable(props) {
    const {form, setLoading, data, reload, openAdd, setOpenAdd, employData, petData, loginUser, bookData} = props;
    const [open, setOpen] = React.useState(false);
    const [currentItem, setCurrentItem] = React.useState(null);
    const [editRow, setEditRow] = React.useState(null);
    const [value, setValue] = React.useState({});
    const [bookOpenAdd, setBookOpenAdd] = React.useState(false);
    const [bookValue, setBookValue] = React.useState({});

    const rows = cloneDeep(data);
    const bookObj = {};
    for(let item of bookData){
        bookObj[item.sId] = item;
    }
    for(let item of rows){
        if(bookObj[item.sId]){
            for(let key of ['cusName','petName','serviceKind','bookId']){
                item[key] = bookObj[item.sId][key];
            }
        }
    }
    const handleClickOpen = (row) => {
        setCurrentItem(row);
        setOpen(true);
    };
    const handleClickBookOpen = (row) => {
        setCurrentItem(row);
        setBookOpenAdd(true);
    };
    const handleClose = () => {
        setOpen(false);
    };
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(8);

    // Avoid a layout jump when reaching the last page with empty rows.
    const emptyRows =
        page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleAdd=async ()=>{
        const params = await form.validateFields();
        params.avaliable = true;
        params.workTime = params.beginTime.format('YYYY-MM-DD HH:mm:ss');
        let result = null;
        setLoading(true);
        if(editRow){
            params.sId = editRow.sId;
            result = await axios.put('/schedules',params);
        }else{
            result = await axios.post('/schedules',params);
        }
        setLoading(false);
        if(get(result,'data.success')){
            message.success(result.data.message);
            setOpenAdd(false);
            reload();
            setEditRow(null);
            setValue({})
        }else{
            message.error(result.data.message);
        }
    };
    const handleDelete = async ()=>{
        setLoading(true);
        const result = await axios.delete(`/schedules/${currentItem.sId}`);
        setLoading(false);
        if(get(result,'data.success')){
            message.success(result.data.message);
            reload();
        }else{
            message.error(result.data.message);
        }
    }
    const handleEdit = async (row)=>{
        const editRow = cloneDeep(row);
        editRow.beginTime = moment(moment().format('YYYY-')+editRow.workTime);
        form.resetFields();
        setEditRow(editRow);
        setOpenAdd(true);
        setValue(editRow);
    }
    const handleBookAdd=async()=>{
        const petObj = {};
        for(let item of petData){
            petObj[item.petId] = item;
        }
        const perItem = petObj[bookValue.petId];
        const params = {
            "cusId": loginUser.cusId,
            "petId": perItem.petId,
            "sId": currentItem.sId,
            "serviceKind": 0
        };
        const bookResult = await axios.post('/books',params);
        if(get(bookResult,'data.success')) {
            message.success(bookResult.data.message);
            setBookValue({});
            setCurrentItem({});
            setBookOpenAdd(false);
            reload();
        }else{
            message.error(bookResult.data.message)
        }
    };
    const handleClickCancelBook=async (currentItem)=>{
        setLoading(true);
        const result = await axios.delete(`/books`,{data:currentItem});
        setLoading(false);
        if(get(result,'data.success')){
            message.success(result.data.message);
            reload();
        }else{
            message.error(result.data.message);
        }
    }
    return (
        <Root sx={{  maxWidth: '100%' }}>
            <Dialog open={openAdd} onClose={()=>setOpenAdd(false)}>
                <DialogTitle>{editRow?'Edit Schedule':'Add Schedule'}</DialogTitle>
                <DialogContent>
                    <Form
                        form={form}
                        name="basic"
                        labelCol={{ span: 8 }}
                        wrapperCol={{ span: 16 }}
                        initialValues={editRow}
                        // onFinish={onFinish}
                        // onFinishFailed={onFinishFailed}
                        autoComplete="off"
                    >
                        <Form.Item
                            label="employee"
                            name="empId"
                            rules={[{ required: true, message: 'Please select your employee!' }]}
                        >
                            <Select>
                                {employData.map(item=> <Select.Option value={item.empId}>{item.empName}</Select.Option>)}
                            </Select>
                        </Form.Item>

                        <Form.Item
                            label="beginTime"
                            name="beginTime"
                            rules={[{ required: true, message: 'Please input your beginTime!' }]}
                        >
                            <DatePicker  showTime style={{width:'100%'}}/>
                        </Form.Item>
                    </Form>
                </DialogContent>
                <DialogActions>
                    <Button onClick={()=>setOpenAdd(false)}>Cancel</Button>
                    <Button onClick={handleAdd}>Subscribe</Button>
                </DialogActions>
            </Dialog>
            <Dialog open={bookOpenAdd} onClose={()=>setBookOpenAdd(false)}>
                <DialogTitle>{editRow?'Edit Book':'Add Book'}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        please input Book's info to add book.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="petId"
                        label="pet"
                        fullWidth
                        variant="standard"
                        value={bookValue.petId}
                        select
                        onChange={(e) => {
                            setBookValue((value)=>({...value,petId:e.target.value}));
                        }}
                    >
                        {petData.map(item=> <MenuItem key={item.petId} value={item.petId}>
                            {item.petName}
                        </MenuItem>)}
                    </TextField>
                </DialogContent>
                <DialogActions>
                    <Button onClick={()=>setBookOpenAdd(false)}>Cancel</Button>
                    <Button onClick={handleBookAdd}>Subscribe</Button>
                </DialogActions>
            </Dialog>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {"Are you sure to delete this pet?"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Delete operation is permanent, are you sure continue！
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Disagree</Button>
                    <Button onClick={handleDelete} autoFocus>
                        Agree
                    </Button>
                </DialogActions>
            </Dialog>
            <table aria-label="custom pagination table">
                <thead>
                <tr>
                    <th>EmployeeName</th>
                    <th>BeginTime</th>
                    <th>EndTime</th>
                    <th>ServiceType</th>
                    <th>PetName</th>
                    <th>CustomerName</th>
                    <th style={{textAlign:'center'}}>Operate</th>
                </tr>
                </thead>
                <tbody>
                {(rowsPerPage > 0
                        ? rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                        : rows
                ).map((row) => (
                    <tr key={row.sId}>
                        <td style={{ width: 120 }}>{row.empName}</td>
                        <td style={{ width: 120 }} align="right">
                            {row.workTime}
                        </td>
                        <td style={{ width: 120 }} align="right">
                            {row.endTime}
                        </td>
                        <td style={{ width: 100 }} align="right">
                            {row.serviceKind===0?'groom':row.serviceKind===1?'bath':''}
                        </td>
                        <td style={{ width: 100 }} align="right">
                            {row.petName}
                        </td>
                        <td style={{ width: 120 }} align="right">
                            {row.cusName}
                        </td>
                        <td style={{ width: 280, textAlign:'right' }} align="right">
                            {!row.petName&&<Button onClick={()=>handleClickBookOpen(row)} variant="outlined" color="success" size='small'>
                                Book
                            </Button>}
                            {row.petName&&row.cusName===loginUser.cusName&&
                            <Button onClick={()=>handleClickCancelBook(row)} variant="outlined" color="success" size='small'>
                                cancelBook
                            </Button>}
                            {get(loginUser,'isAdmin')&&<Button onClick={()=>handleEdit(row)} style={{marginLeft:5}} variant="outlined" color="success" size='small'>
                                Edit
                            </Button>}
                            {get(loginUser,'isAdmin')&&<Button onClick={()=>handleClickOpen(row)} style={{marginLeft:5}} variant="outlined" color="error" size='small'>
                                Delete
                            </Button>}
                        </td>
                    </tr>
                ))}

                {emptyRows > 0 && (
                    <tr style={{ height: 41 * emptyRows }}>
                        <td colSpan={7} />
                    </tr>
                )}
                </tbody>
                <tfoot>
                <tr>
                    <CustomTablePagination
                        rowsPerPageOptions={[8]}
                        colSpan={7}
                        count={rows.length}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        componentsProps={{
                            select: {
                                'aria-label': 'rows per page',
                            },
                            actions: {
                                showFirstButton: true,
                                showLastButton: true,
                            },
                        }}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                    />
                </tr>
                </tfoot>
            </table>
        </Root>
    );
}
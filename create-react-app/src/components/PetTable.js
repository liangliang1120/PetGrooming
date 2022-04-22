import * as React from 'react';
import { styled } from '@mui/system';
import TablePaginationUnstyled from '@mui/base/TablePaginationUnstyled';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {Button, TextField} from "@mui/material";
import { DatePicker } from '@mui/lab';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import MenuItem from '@mui/material/MenuItem';
import axios from 'axios';
import moment from 'moment';
import { message } from 'antd';
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
    const {data:rows, reload, openAdd, setOpenAdd, loginUser, setLoading} = props;
    const [open, setOpen] = React.useState(false);
    const [editRow, setEditRow] = React.useState(null);
    const [currentItem, setCurrentItem] = React.useState(null);
    const [value, setValue] = React.useState({birthday:null});

    const handleClickOpen = (row) => {
        setCurrentItem(row);
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);

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
        const params = cloneDeep(value);
        params.cusId = loginUser.cusId;
        params.birthday = moment(value.birthday).format('YYYY-MM-DD');
        let result = null;
        setLoading(true);
        if(editRow){
            params.petId = editRow.petId;
            result = await axios.put('/pets',params);
        }else{
            result = await axios.post('/pets',params);
        }
        setLoading(false);
        if(get(result,'data.success')){
            message.success(result.data.message)
            setOpenAdd(false);
            reload();
            setValue({birthday:null});
            setEditRow(null);
        }else{
            message.error(result.data.message)
        }
    };
    const handleDelete = async ()=>{
        setLoading(true);
        const result = await axios.delete(`/pets/${currentItem.petId}`);
        setLoading(false);
        if(get(result,'data.success')){
            message.success(result.data.message)
            handleClose();
            reload();
        }else{
            message.error(result.data.message)
        }
    }
    const handleEdit = async (row)=>{
        setEditRow(row);
        setOpenAdd(true);
        setValue(row);
    }
    return (
        <Root sx={{ width: 500, maxWidth: '100%' }}>
            <Dialog open={openAdd} onClose={()=>setOpenAdd(false)}>
                <DialogTitle>{editRow?'Edit Your Pet':'Add Your Pet'}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                       Please input your pet's info.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="petName"
                        label="petName"
                        fullWidth
                        variant="standard"
                        value={value.petName}
                        onChange={(e) => {
                            setValue((value)=>({...value,petName:e.target.value}));
                        }}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="gender"
                        label="gender"
                        fullWidth
                        variant="standard"
                        value={value.gender}
                        select
                        onChange={(e) => {
                            setValue((value)=>({...value,gender:e.target.value}));
                        }}
                    >
                        <MenuItem key={false} value={false}>
                            {'male'}
                        </MenuItem>
                        <MenuItem key={true} value={true}>
                            {'female'}
                        </MenuItem>
                    </TextField>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DatePicker
                        label="birthday"
                        value={value.birthday}
                        onChange={(newValue) => {
                            setValue((value)=>({...value,birthday:newValue}));
                        }}
                        renderInput={(params) =>
                            <TextField
                                autoFocus
                                margin="dense"
                                fullWidth
                                variant="standard"
                                {...params}
                            />}
                    />
                    </LocalizationProvider>
                </DialogContent>
                <DialogActions>
                    <Button onClick={()=>setOpenAdd(false)}>Cancel</Button>
                    <Button onClick={handleAdd}>Subscribe</Button>
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
                     Delete operation is permanent, you may lost your pet forever！
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
                    <th>PetName</th>
                    <th>Gender</th>
                    <th>Birthday</th>
                    <th>Operate</th>
                </tr>
                </thead>
                <tbody>
                {(rowsPerPage > 0
                        ? rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                        : rows
                ).map((row) => (
                    <tr key={row.petId}>
                        <td>{row.petName}</td>
                        <td style={{ width: 120 }} align="right">
                            {row.gender?'female':'male'}
                        </td>
                        <td style={{ width: 120 }} align="right">
                            {row.birthday}
                        </td>
                        <td style={{ width: 160 }} align="right">
                            <Button onClick={()=>handleEdit(row)} variant="outlined" color="success" size='small'>
                                Edit
                            </Button>
                            <Button onClick={()=>handleClickOpen(row)} style={{marginLeft:5}} variant="outlined" color="error" size='small'>
                                Delete
                            </Button>
                        </td>
                    </tr>
                ))}

                {emptyRows > 0 && (
                    <tr style={{ height: 41 * emptyRows }}>
                        <td colSpan={4} />
                    </tr>
                )}
                </tbody>
                <tfoot>
                <tr>
                    <CustomTablePagination
                        rowsPerPageOptions={[5]}
                        colSpan={4}
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
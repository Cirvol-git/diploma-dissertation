import axios from 'axios'
import M from "materialize-css";

export const getAll = () => {
    return (dispatch, getState) => {
        axios.get("http://localhost:8080/api")
            .then(res => {
                console.log("getAll()", res);
                dispatch({ type: "GET_ALL", res: res.data })
            }).catch(error => {
                console.log("error", error.response);
                if (error.response) {
                    M.toast({ html: error.response.data, classes: 'teal' });
                }
            })
    }
}

export const getFix = () => {
    return (dispatch, getState) => {
        axios.get("http://localhost:8080/api/fix")
            .then(res => {
                console.log("getFix()", res);
                M.toast({ html: 'File fixed successfully with ' + res.data.errorCount + ' error(s)', classes: 'teal' });
                dispatch({ type: "GET_FIX", res: res.data })
            }).catch(error => {
                console.log("error", error.response);
                M.toast({ html: error.response.data, classes: 'teal' });
            })
    }
}

export const getDownload = () => {
    return (dispatch, getState) => {
        axios({
            url: 'http://localhost:8080/api/download',
            method: 'GET',
            responseType: 'blob', // important
        })
            .then(res => {
                const url = window.URL.createObjectURL(new Blob([res.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', 'fixed.d64'); //or any other extension
                document.body.appendChild(link);
                link.click();
                // dispatch({ type: "GET_DOWNLOAD", res: res.data })
            }).catch(error => {
                console.log("error", error.response);
                M.toast({ html: error.response.data, classes: 'teal' });
            })
    }
}

export const uploadFile = (file) => {
    return (dispatch, getState) => {
        const formData = new FormData();

        formData.append("file", file);

        axios.post("http://localhost:8080/api", formData)
            .then(res => {
                console.log("results of upload", res);
                M.toast({ html: 'File successfully uploaded', classes: 'teal' });
                dispatch({ type: "UPLOAD_FILE", res: res.data });
            }).catch(error => {
                console.log("error", error.response);
                M.toast({ html: error.response.data, classes: 'teal' });
            })
    }
}

export const removeFix = () => {
    return (dispatch, getState) => {
        axios.delete("http://localhost:8080/api/fix")
            .then(res => {
                console.log("removeFix()", res);
                M.toast({ html: 'Fixed file successfully deleted', classes: 'teal' });
                dispatch({ type: "REMOVE_FIX" })
            }).catch(error => {
                console.log("error", error.response);
                M.toast({ html: error.response.data, classes: 'teal' });
            })
    }
}

export const removeOne = (i) => {
    return (dispatch, getState) => {
        axios.delete("http://localhost:8080/api/" + i)
            .then(res => {
                console.log("removeOne(" + i + ")", res);
                M.toast({ html: 'File ' + getState().files[i].name + ' successfully deleted', classes: 'teal' });
                dispatch({ type: "REMOVE_ONE", i })
            }).catch(error => {
                console.log("error", error.response);
                M.toast({ html: error.response.data, classes: 'teal' });
            })
    }
}

export const removeAll = () => {
    return (dispatch, getState) => {
        axios.delete("http://localhost:8080/api")
            .then(res => {
                console.log("removeAll()", res);
                M.toast({ html: 'All files successfully deleted', classes: 'teal' });
                dispatch({ type: "REMOVE_ALL" })
            }).catch(error => {
                console.log("error", error.response);
                M.toast({ html: error.response.data, classes: 'teal' });
            })
    }
}

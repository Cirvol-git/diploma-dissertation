import axios from 'axios'

export const getAll = () => {
    return (dispatch, getState) => {
        axios.get("http://localhost:8080/api")
            .then(res => {
                console.log("getAll()", res);
                dispatch({ type: "GET_ALL", res: res.data })
            }).catch(error => {
                console.log("error", error.response);
                alert(error.response.data)
            })
    }
}

export const getFix = () => {
    return (dispatch, getState) => {
        axios.get("http://localhost:8080/api/fix")
            .then(res => {
                console.log("getFix()", res);
                alert("File fixed successfully with " + res.data.errorCount + " errors")
                dispatch({ type: "GET_FIX", res: res.data })
            }).catch(error => {
                console.log("error", error.response);
                alert(error.response.data)
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
                alert(error.response.data)
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
                alert("File successfully uploaded.");
                dispatch({ type: "UPLOAD_FILE", res: res.data });
            }).catch(error => {
                console.log("error", error.response);
                alert(error.response.data)
            })
    }
}

export const removeFix = () => {
    return (dispatch, getState) => {
        axios.delete("http://localhost:8080/api/fix")
            .then(res => {
                console.log("removeFix()", res);
                dispatch({ type: "REMOVE_FIX" })
            }).catch(error => {
                console.log("error", error.response);
                alert(error.response.data)
            })
    }
}

export const removeOne = (i) => {
    return (dispatch, getState) => {
        axios.delete("http://localhost:8080/api/" + i)
            .then(res => {
                console.log("removeOne(" + i + ")", res);
                dispatch({ type: "REMOVE_ONE", i })
            }).catch(error => {
                console.log("error", error.response);
                alert(error.response.data)
            })
    }
}

export const removeAll = () => {
    return (dispatch, getState) => {
        axios.delete("http://localhost:8080/api")
            .then(res => {
                console.log("removeAll()", res);
                dispatch({ type: "REMOVE_ALL" })
            }).catch(error => {
                console.log("error", error.response);
                alert(error.response.data)
            })
    }
}

const initState = {
    files: [],
    fixed: null,
    forDownload: null
}

const RootReducer = (state = initState, action) => {
    console.log(action);
    switch (action.type) {
        case "GET_ALL":
            return {
                ...state,
                files: action.res
            }
        case "GET_FIX":
            return {
                ...state,
                fixed: action.res
            }
        case "GET_DOWNLOAD":
            return {
                ...state,
                forDownload: action.res
            }
        case "UPLOAD_FILE":
            return {
                ...state,
                files: [...state.files, action.res]
            }
        case "REMOVE_FIX":
            return {
                ...state,
                fixed: null
            }
        case "REMOVE_ONE":
            return {
                ...state,
                files: state.files.filter((x, ind, a) => ind != action.i)
            }
        case "REMOVE_ALL":
            return {
                ...state,
                files: [],
                fixed: null
            }

        default:
            return state;
    }
}

export default RootReducer
import React, { Component } from 'react';
import { uploadFile, getFix, getAll, getDownload, removeOne, removeAll, removeFix } from '../actions/FileActions';
import { connect } from 'react-redux';

class UploadTab extends Component {

    state = {
        selectedFile: null,
        selectedForView: null
    }

    componentDidMount() {
        this.props.getAll();
    }

    handleChange = (e) => {
        //e.persist();
        this.setState({
            ...this.state,
            [e.target.id]: e.target.files[0]
        })
    }

    handleView = (e) => {
        if (e.target.value.localeCompare("fix")) {
            this.setState({
                ...this.state,
                [e.target.id]: this.props.files[e.target.value]
            })
        } else {
            this.setState({
                ...this.state,
                [e.target.id]: this.props.fixed
            })
        }
    }

    handleSubmit = (e) => {
        e.preventDefault();
        console.log(this.state.selectedFile);
        if (this.state.selectedFile) {
            this.props.uploadFile(this.state.selectedFile);
        } else {
            alert("You must first select file to upload.")
        }

    }

    handleRemoveOne = (ind) => {
        let removed = this.props.files[ind];
        if (this.state.selectedForView != null && removed != null) {
            if (!this.state.selectedForView.name.localeCompare(removed.name) && ind + 1 < this.props.files.length) {
                this.setState({
                    ...this.state,
                    selectedForView: this.props.files[ind + 1]
                })
            } else {
                this.setState({
                    ...this.state,
                    selectedForView: null
                })
            }
        } else {
            this.setState({
                ...this.state,
                selectedForView: null
            })
        }
        if (ind == 10) {
            this.props.removeFix();
        } else {
            this.props.removeOne(ind);
        }
    }

    handleRemoveAll = () => {
        this.setState({
            ...this.state,
            selectedForView: null
        })
        this.props.removeAll();
    }

    render() {

        const headers = Array.apply(null, { length: 21 }).map(Number.call, Number).map(x => {
            return (
                <th key={x}>
                    {x}
                </th>
            )
        })

        const tracks = this.state.selectedForView && this.state.selectedForView.sectorView.map(track => {
            return (
                <tr>
                    {track.map(sector => {
                        return (
                            <td className={sector ? "green" : "red"}>

                            </td>
                        )
                    })}
                </tr>
            )
        })

        const fileList = this.props.files.map((file, ind, a) => {
            return (
                <li key={ind} className="collection-item row halign-wrapper">

                    <label className="left">
                        <input name="group1" type="radio" id="selectedForView" value={ind} onChange={this.handleView} />
                        <span>{file.name}</span>
                    </label>

                    <i onClick={() => { this.handleRemoveOne(ind) }} className="secondary-content material-icons">cancel</i>
                </li>
            )
        })

        return (
            <div>
                <div className="row pomeri">
                    <div className="col s8 m4 l3">
                        <form onSubmit={this.handleSubmit} >
                            <div className="row">
                                <div className="file-field input-field col s10 m10 l10">
                                    <div className="btn ">
                                        <span><i className="material-icons">add</i></span>
                                        <input type="file" id="selectedFile" onChange={this.handleChange} />
                                    </div>
                                    <div className="file-path-wrapper">
                                        <input className="file-path validate" type="text" />
                                    </div>
                                </div>
                                <div className="input-field upload col s2 m2 l2">
                                    <button className="btn "><i className="material-icons">file_upload</i></button>
                                </div>
                            </div>
                        </form>
                        {this.props.files.length || this.props.fixed ?
                            <ul className="collection with-header container">
                                <li key="12" className="collection-header">
                                    <i onClick={() => { this.handleRemoveAll() }} className="secondary-content material-icons">cancel</i>
                                    <h5>File list</h5>
                                </li>
                                {fileList}
                            </ul>
                            :
                            null
                        }
                        {this.props.files.length >= 2 ?
                            <button onClick={this.props.getFix} className="btn btn-success uvuci">Try fixing file!</button>
                            :
                            null
                        }
                        {this.props.fixed ?
                            <div className="uvucix2">
                                <ul className="collection container ">
                                    <li className="collection-item row halign-wrapper">

                                        <label className="left">
                                            <input name="group1" type="radio" id="selectedForView" value="fix" onChange={this.handleView} />
                                            <span>{this.props.fixed.name}</span>
                                        </label>

                                        <i onClick={() => { this.handleRemoveOne(10) }} className="secondary-content material-icons">cancel</i>
                                    </li>
                                </ul>
                                <button onClick={this.props.getDownload} className="btn btn-success uvuci">Download</button>
                            </div>
                            :
                            null
                        }
                    </div>
                    <div className="col s4 m8 l9 container">
                        <table>
                            <tbody>
                                {tracks}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return {
        files: state.files,
        fixed: state.fixed,
        forDownload: state.forDownload
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        getAll: () => dispatch(getAll()),
        getFix: () => dispatch(getFix()),
        getDownload: () => dispatch(getDownload()),
        uploadFile: (file) => dispatch(uploadFile(file)),
        removeFix: (i) => dispatch(removeFix(i)),
        removeOne: (i) => dispatch(removeOne(i)),
        removeAll: () => dispatch(removeAll())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(UploadTab)
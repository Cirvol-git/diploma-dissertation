import React from 'react';
import './App.css';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import UploadTab from './components/UploadTab';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Switch>
          <Route exact path='/' component={UploadTab}></Route>
        </Switch>
      </div>
    </BrowserRouter>
  );
}

export default App;

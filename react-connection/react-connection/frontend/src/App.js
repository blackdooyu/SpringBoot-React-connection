import './App.css';
import React, {useState, useEffect} from 'react';
import axios from 'axios';

function App() {

  useEffect(() =>{
    getDate();
  }, []);

  async function getDate(){
      await axios.get("http://localhost:8080/hello")
      .then((response) =>{
        console.log(response.data)
      })
      .catch((error) => {
        console.error(error)
      })
    
  }
  return (
    <div className="App">
    
    </div>
  );
}

export default App;
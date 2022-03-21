import './App.css';
import React, {useState, useEffect} from 'react';
import axios from 'axios';

function App() {


  const [input,setInput] = useState("");
  const [users, setUsers] = useState([]);
  
  const baseUrl = "http://localhost:8080";

  useEffect(() =>{
    getData();
  }, []);

    function changeText(e){
      e.preventDefault();
      setInput(e.target.value);
    }

  async function getData(){
      await axios.get(baseUrl+"/todo")
      .then((response) =>{
        setUsers(response.data);
      })
      .catch((error) => {
        console.error(error);
      })

  }

  function insertTodo(e){
    e.preventDefault();

    const insertTodo = async () => {
      await axios.post(baseUrl+"/todo",{
        name: input
      })
      .then((response) => {
        console.log(response.data)
        setInput("");
        getData();
      })
      .catch((error) =>{
        console.error(error);
      })
    }
    insertTodo();
    console.log("할 일이 추가됐습니다.")
  }

  function deleteTodo(id){
    const deleteTodo = async () => {
      await axios.delete(baseUrl+"/todo",{
        data: {
          id: id
        }
        
      })
      .then((response) => {
        getData();
      })
      .catch((error) => {
        console.error(error);
      })
    }
    deleteTodo();
  }

  function updateTodo(id){
    const updateTodo = async () => {
      await axios.put(baseUrl+"/todo",{
        id: id
      })
      .then((response) => {
        getData();
      })
      .catch((error) =>{
        console.error(error);
      })
    }
    updateTodo();
    console.log("할 일을 완료했습니다.")
    
  }


  return (
    <div className="App">
      <h1>TODO List</h1>
      <form onSubmit={insertTodo}>
        Todo &nbsp; 
        <input type="text"
        required={true}
        value={input}
        onChange={changeText}
        />
        <input type= "submit"
        value="Create"/>
      </form>


      {
        users 
        ? users.map((user) =>{
          return(
            <div className='todo' key={user.id}>
              <h3>  
                <label
                onClick={() => updateTodo(user.id)}
                className={user.complete === 'Y' ? "completed" : null }
               >
              {user.todoName} {user.complete}
               </label>
      
               <label
              onClick={() => deleteTodo(user.id)}
              >
                &nbsp; &nbsp;  ❌
              </label>
              </h3>

              </div>
          )
        }) 
        : null

      }
    </div>
  );
}

export default App;
import './App.css';
import { useState } from 'react';
import axios from 'axios';

function App() {
  const [inputValue, setInputValue] = useState("");
  const [messages, setMessages] = useState([
  ]);

  const sendMessageToServer = async (message) => {
    const response = await axios.post("http://localhost:8080/api/getMessage", message);
    return response.data
  }

  const handleSendMessage = async () => {
    if (inputValue.trim() !== "") {
      setMessages(prevMessages => [...prevMessages, { author: 'user', content: inputValue }]);
      const response = await sendMessageToServer(inputValue);
      setMessages(prevMessages => [...prevMessages, { author: 'bot', content: response }]);
      setInputValue("");
    }
  };

  return (
    <div className="App">
      <div className="chat">
        <div className="conversation">
          {messages.map((message, index) => (
            <div key={index} className={message.author}>
              {message.content}
            </div>
          ))}
        </div>
        <div className="input-container">
          <input type='text' id="text-area" value={inputValue} onChange={(e) => setInputValue(e.target.value)} />
          <button id="send-button" onClick={handleSendMessage}>Send</button>
        </div>
      </div>
    </div>
  );
}

export default App;
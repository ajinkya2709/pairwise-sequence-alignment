import React, { Component } from "react";
import logo from "./logo.svg";
import "./App.css";
import AlignmentInputMenu from "./components/AlignmentInputMenu";

class App extends Component {
  computeAlignment(data) {
    console.log("Serialized JSON Data", JSON.stringify(data));
    fetch("/global", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Accept: "application/json"
      },
      body: JSON.stringify(data)
    })
      .then(response => {
        return response.json();
      })
      .then(json => {
        console.log("json", json);
      });
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Pairwise Sequence Alignment</h1>
        </header>
        <div>
          <AlignmentInputMenu onSubmit={this.computeAlignment.bind(this)} />
        </div>
      </div>
    );
  }
}

export default App;

import React, { Component } from "react";
import logo from "./logo.svg";
import "./App.css";
import AlignmentInputMenu from "./components/AlignmentInputMenu";
import ScoreMatrix from "./components/ScoreMatrix";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      sequence1: [],
      sequence2: [],
      scoreMatrix: []
    };
  }

  computeAlignment(data) {
    // State change is asynchronous
    this.setState({
      sequence1: data.input1.split(""),
      sequence2: data.input2.split("")
    });
    fetch("/" + data.alignmentType, {
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
        this.setState({ scoreMatrix: json.scoreMatrix });
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
        <div>
          <ScoreMatrix
            matrix={this.state.scoreMatrix}
            sequence1={this.state.sequence1}
            sequence2={this.state.sequence2}
          />
        </div>
      </div>
    );
  }
}

export default App;

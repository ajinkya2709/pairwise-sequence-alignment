import React, { Component } from "react";
import logo from "./logo.svg";
import "./App.css";
import AlignmentInputMenu from "./components/AlignmentInputMenu";
import ScoreMatrix from "./components/ScoreMatrix";
import AffineGapScoreMatrices from "./components/AffineGapScoreMatrices";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      sequence1: [],
      sequence2: [],
      scoreMatrix: [],
      scoreMatrixM: [],
      scoreMatrixX: [],
      scoreMatrixY: []
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
        if (!response.ok) {
          throw Error(response.statusText);
        }
        return response.json();
      })
      .then(json => {
        console.log("json", json);
        if (!json.affineModelUsed) {
          this.setState({
            scoreMatrix: json.scoreMatrix,
            scoreMatrixM: [],
            scoreMatrixX: [],
            scoreMatrixY: []
          });
        } else {
          this.setState({
            scoreMatrixM: json.scoreMatrixM,
            scoreMatrixX: json.scoreMatrixX,
            scoreMatrixY: json.scoreMatrixY,
            scoreMatrix: []
          });
        }
      })
      .catch(function(error) {
        console.log(error);
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
          <AffineGapScoreMatrices
            matrixM={this.state.scoreMatrixM}
            matrixX={this.state.scoreMatrixX}
            matrixY={this.state.scoreMatrixY}
            sequence1={this.state.sequence1}
            sequence2={this.state.sequence2}
          />
        </div>
      </div>
    );
  }
}

export default App;

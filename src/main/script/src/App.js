import React, { Component } from "react";
import logo from "./logo.svg";
import "./App.css";
import AlignmentInputMenu from "./components/AlignmentInputMenu";
import ScoreMatrix from "./components/ScoreMatrix";
import AffineGapScoreMatrices from "./components/AffineGapScoreMatrices";
import { Tabs, Tab } from "react-bootstrap";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      sequence1: [],
      sequence2: [],
      scoreMatrix: [],
      scoreMatrixM: [],
      scoreMatrixX: [],
      scoreMatrixY: [],
      activeTab: 1
    };
  }

  computeAlignment(data) {
    // Updating flag for protein/dna to be sent to backend
    if (this.state.activeTab === 1) {
      data.forProteins = false;
    } else {
      data.forProteins = true;
    }
    console.log("data", data);
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
        <div className="App-input-menu">
          <Tabs
            activeKey={this.state.activeTab}
            onSelect={key => this.setState({ activeTab: key })}
            id="controlled-tab-example"
          >
            <Tab eventKey={1} title="Nucleotides">
              <AlignmentInputMenu
                onSubmit={this.computeAlignment.bind(this)}
                disableBlosum={false}
              />
            </Tab>
            <Tab eventKey={2} title="Proteins">
              <AlignmentInputMenu
                onSubmit={this.computeAlignment.bind(this)}
                disableBlosum={true}
              />
            </Tab>
          </Tabs>
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

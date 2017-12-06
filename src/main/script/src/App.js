import React, { Component } from "react";
import logo from "./logo.svg";
import "./App.css";
import AlignmentInputMenu from "./components/AlignmentInputMenu";
import ScoreMatrix from "./components/ScoreMatrix";
import AffineGapScoreMatrices from "./components/AffineGapScoreMatrices";
import AlignedSequences from "./components/AlignedSequences";
import AlgoInfo from "./components/AlgoInfo";
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
      activeTab: 1,
      alignedSequence1: [],
      alignedSequence2: [],
      showLocalAlignmentInfo: false,
      showGlobalAlignmentInfo: false,
      affineModelUsed: false
    };
  }

  sendRequestForFileInput(data) {
    const file1 = data.file1;
    const file2 = data.file2;
    let formData = new FormData();
    formData.append("file1", file1);
    formData.append("file2", file2);
    formData.append(
      "requestData",
      new Blob([JSON.stringify(data)], {
        type: "application/json"
      })
    );
    fetch("/" + data.alignmentType + "WithFile", {
      method: "POST",
      body: formData
    })
      .then(response => {
        if (!response.ok) {
          throw Error(response.statusText);
        }
        return response.json();
      })
      .then(json => {
        console.log("json", json);
        if (json.fileInvalid) {
          console.log("File Invalid");
          alert(json.errorMessage);
        } else {
          if (!json.affineModelUsed) {
            this.setState({
              scoreMatrix: json.scoreMatrix,
              scoreMatrixM: [],
              scoreMatrixX: [],
              scoreMatrixY: [],
              sequence1: String(json.input1).split(""),
              sequence2: String(json.input2).split(""),
              alignedSequence1: String(json.sequence1).split(""),
              alignedSequence2: String(json.sequence2).split(""),
              showLocalAlignmentInfo: !json.global,
              showGlobalAlignmentInfo: json.global,
              affineModelUsed: false
            });
          } else {
            this.setState({
              scoreMatrixM: json.scoreMatrixM,
              scoreMatrixX: json.scoreMatrixX,
              scoreMatrixY: json.scoreMatrixY,
              scoreMatrix: [],
              sequence1: String(json.input1).split(""),
              sequence2: String(json.input2).split(""),
              alignedSequence1: String(json.sequence1).split(""),
              alignedSequence2: String(json.sequence2).split(""),
              showLocalAlignmentInfo: !json.global,
              showGlobalAlignmentInfo: json.global,
              affineModelUsed: true
            });
          }
        }
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  computeAlignment(data) {
    // Updating flag to identify protein vs dna
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

    // Redirecting request if file was uploaded
    if (data.useFileInput) {
      this.sendRequestForFileInput(data);
      return;
    }

    // For normal cases
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
            scoreMatrixY: [],
            alignedSequence1: String(json.sequence1).split(""),
            alignedSequence2: String(json.sequence2).split(""),
            showLocalAlignmentInfo: !json.global,
            showGlobalAlignmentInfo: json.global,
            affineModelUsed: false
          });
        } else {
          this.setState({
            scoreMatrixM: json.scoreMatrixM,
            scoreMatrixX: json.scoreMatrixX,
            scoreMatrixY: json.scoreMatrixY,
            scoreMatrix: [],
            alignedSequence1: String(json.sequence1).split(""),
            alignedSequence2: String(json.sequence2).split(""),
            showLocalAlignmentInfo: !json.global,
            showGlobalAlignmentInfo: json.global,
            affineModelUsed: true
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
        <div className="Score-matrices-div">
          <ScoreMatrix
            title={"Score Matrix M"}
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
        <div className="Aligned-sequences-div">
          <AlignedSequences
            alignedSequence1={this.state.alignedSequence1}
            alignedSequence2={this.state.alignedSequence2}
          />
        </div>
        <div>
          <AlgoInfo
            showLocalAlignmentInfo={this.state.showLocalAlignmentInfo}
            showGlobalAlignmentInfo={this.state.showGlobalAlignmentInfo}
            affineModelUsed={this.state.affineModelUsed}
          />
        </div>
      </div>
    );
  }
}

export default App;

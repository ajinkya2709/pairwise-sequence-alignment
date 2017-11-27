import React, { Component } from "react";

class AlignmentInputMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      input1: "",
      input2: "",
      matchScore: 0,
      mismatchScore: 0,
      gapPenalty: 0,
      useBlosumMatrix: false,
      useAffineGapPenalty: false,
      gapStartPenalty: 0,
      gapExtendPenalty: 0
    };
  }

  render() {
    return (
      <div className="Alignment-input-menu">
        <div className="Alignment-input-options">
          <div className="Option-header">Input Options</div>
          <div className="Alignment-sequence-input-div">
            Sequence 1 :{" "}
            <input
              className="Text-input"
              type="text"
              onChange={event => this.setState({ input1: event.target.value })}
            />
            Sequence 2 :{" "}
            <input
              className="Text-input"
              type="text"
              onChange={event => this.setState({ input2: event.target.value })}
            />
          </div>
          <div className="Alignment-file-input">Div for File Input</div>
        </div>
        <div className="Alignment-score-options">
          <div className="Option-header">Score Options</div>
          <div className="Alignment-simple-score-input-div">
            Match Score :{" "}
            <input
              className="Text-input"
              type="text"
              onChange={event =>
                this.setState({ matchScore: event.target.value })
              }
            />
            Mismatch Score :{" "}
            <input
              className="Text-input"
              type="text"
              onChange={event =>
                this.setState({ mismatchScore: event.target.value })
              }
            />
          </div>
          <div>Div for selecting Blosum Scoring</div>
        </div>
        <div className="Alignment-penalty-options">
          {" "}
          <div className="Option-header">Penalty Options</div>
          <div className="Alignment-constant-penalty-input-div">
            Gap Penalty :{" "}
            <input
              className="Text-input"
              type="text"
              onChange={event =>
                this.setState({ matchScore: event.target.value })
              }
            />
          </div>
          <div>Div for Affine Gap Penalty</div>
        </div>
        <div>
          <button className="Submit-button" type="button">
            Compute Alignment
          </button>
        </div>
      </div>
    );
  }
}

export default AlignmentInputMenu;

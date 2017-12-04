import React, { Component } from "react";
import { Collapse } from "react-bootstrap";

class AlignmentInputMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      input1: "",
      input2: "",
      matchScore: 0,
      mismatchScore: 0,
      gapPenalty: 0,
      gapStartPenalty: 0,
      gapExtendPenalty: 0,
      alignmentType: "global",
      // Control Input Section
      // This boolean should control the request url specific to files
      useFileInput: false,
      inputType: "text",
      // Control the Scoring Section
      useBlosumMatrix: false,
      scoringType: "simple",
      // Control the Gap Penalty Section
      useAffineGapPenalty: false,
      penaltyType: "simple"
    };
  }

  render() {
    return (
      <div className="Alignment-input-menu">
        <div className="Alignment-input-options">
          <div className="Option-header">
            Input Options{" "}
            <select
              // change class name if needed
              className="Alignment-type-dropdown"
              onChange={event => {
                if (event.target.value === "text") {
                  this.setState({ useFileInput: false, inputType: "text" });
                } else {
                  this.setState({ useFileInput: true, inputType: "file" });
                }
              }}
            >
              <option value="text">Text Input</option>
              <option value="file">File Input</option>
            </select>
          </div>
          <Collapse in={!this.state.useFileInput}>
            <div className="Alignment-sequence-input-div">
              Sequence 1 :{" "}
              <input
                className="Text-input"
                type="text"
                onChange={event =>
                  this.setState({ input1: event.target.value })
                }
              />
              Sequence 2 :{" "}
              <input
                className="Text-input"
                type="text"
                onChange={event =>
                  this.setState({ input2: event.target.value })
                }
              />
            </div>
          </Collapse>
          <Collapse in={this.state.useFileInput}>
            <div className="Alignment-file-input">
              <form encType="multipart/form-data" action="">
                <input
                  className="File-input"
                  type="file"
                  name="fileName"
                  defaultValue="fileName"
                />
              </form>
            </div>
          </Collapse>
        </div>
        <div className="Alignment-score-options">
          <div className="Option-header">
            Score Options{" "}
            <select
              // change class name if needed
              className="Alignment-type-dropdown"
              onChange={event => {
                if (event.target.value === "simple") {
                  this.setState({
                    useBlosumMatrix: false,
                    scoringType: "simple"
                  });
                } else {
                  this.setState({
                    useBlosumMatrix: true,
                    scoringType: "blosum"
                  });
                }
              }}
            >
              <option value="simple">Text Input</option>
              <option value="blosum" disabled={this.props.disableBlosum}>
                Blosum 62
              </option>
            </select>
          </div>
          <Collapse in={!this.state.useBlosumMatrix}>
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
          </Collapse>
          <Collapse in={this.state.useBlosumMatrix}>
            <div className="Alignment-blosum-div">
              <input type="checkbox" disabled checked /> Use Blosum 62 Matrix
            </div>
          </Collapse>
        </div>
        <div className="Alignment-penalty-options">
          {" "}
          <div className="Option-header">
            Penalty Options{" "}
            <select
              // change class name if needed
              className="Alignment-type-dropdown"
              onChange={event => {
                if (event.target.value === "simple") {
                  this.setState({
                    useAffineGapPenalty: false,
                    penaltyType: "simple"
                  });
                } else {
                  this.setState({
                    useAffineGapPenalty: true,
                    penaltyType: "affine"
                  });
                }
              }}
            >
              <option value="simple">Constant Gap Penalty</option>
              <option value="affine">Affine Gap Model</option>
            </select>
          </div>
          <Collapse in={!this.state.useAffineGapPenalty}>
            <div className="Alignment-constant-penalty-input-div">
              Gap Penalty :{" "}
              <input
                className="Text-input"
                type="text"
                onChange={event =>
                  this.setState({ gapPenalty: event.target.value })
                }
              />
            </div>
          </Collapse>
          <Collapse in={this.state.useAffineGapPenalty}>
            <div>
              Gap Start Penalty :{" "}
              <input
                className="Text-input"
                type="text"
                onChange={event =>
                  this.setState({ gapStartPenalty: event.target.value })
                }
              />
              Gap Extend Penalty :{" "}
              <input
                className="Text-input"
                type="text"
                onChange={event =>
                  this.setState({ gapExtendPenalty: event.target.value })
                }
              />
            </div>
          </Collapse>
        </div>
        <div>
          <select
            className="Alignment-type-dropdown"
            defaultValue={this.state.alignmentType}
            onChange={event =>
              this.setState({ alignmentType: event.target.value })
            }
          >
            <option value="global">Global Alignment</option>
            <option value="local">Local Alignment</option>
          </select>
          <button
            className="Submit-button"
            type="button"
            onClick={() => {
              this.props.onSubmit(this.state);
            }}
          >
            Compute Alignment
          </button>
        </div>
      </div>
    );
  }
}

export default AlignmentInputMenu;

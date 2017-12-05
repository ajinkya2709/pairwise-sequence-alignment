import React, { Component } from "react";
import { Button, Collapse, Modal } from "react-bootstrap";

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
      file1: null,
      file2: null,
      // Control the Scoring Section
      useBlosumMatrix: false,
      scoringType: "simple",
      // Control the Gap Penalty Section
      useAffineGapPenalty: false,
      penaltyType: "simple",
      // For Modals
      showInputModal: false,
      showScoringModal: false,
      showPenaltyModal: false
    };
  }

  render() {
    const modalForInput = (
      <Modal
        show={this.state.showInputModal}
        onHide={() => this.setState({ showInputModal: false })}
      >
        <Modal.Header closeButton>
          <Modal.Title>Valid Inputs</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h4>Text in a modal</h4>
          <p>
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
          </p>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={() => this.setState({ showInputModal: false })}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    );

    const modalForScoring = (
      <Modal
        show={this.state.showScoringModal}
        onHide={() => this.setState({ showScoringModal: false })}
      >
        <Modal.Header closeButton>
          <Modal.Title>Scoring Schemes</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h4>Text related to scoring</h4>
          <p>
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
          </p>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={() => this.setState({ showScoringModal: false })}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    );

    const modalForPenalty = (
      <Modal
        show={this.state.showPenaltyModal}
        onHide={() => this.setState({ showPenaltyModal: false })}
      >
        <Modal.Header closeButton>
          <Modal.Title>Gap Penalties</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h4>Text related to penalties</h4>
          <p>
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
          </p>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={() => this.setState({ showPenaltyModal: false })}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    );

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
            <Button
              bsStyle="info"
              onClick={() => this.setState({ showInputModal: true })}
            >
              Input Tips
            </Button>
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
            <div className="Alignment-file-input-div">
              <div className="File-input-text">File for Sequence 1 :</div>
              <div className="File-input-text">File for Sequence 2 :</div>
              <form
                name="formWithFiles"
                encType="multipart/form-data"
                action=""
              >
                <input
                  type="file"
                  id="file1"
                  className="File-input"
                  onChange={event =>
                    this.setState({ file1: event.target.files[0] })
                  }
                />
                <input
                  type="file"
                  id="file2"
                  className="File-input"
                  onChange={event =>
                    this.setState({ file2: event.target.files[0] })
                  }
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
            <Button
              bsStyle="info"
              onClick={() => this.setState({ showScoringModal: true })}
            >
              Scoring Tips
            </Button>
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
            Penalty Model{" "}
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
              <option value="simple">Constant</option>
              <option value="affine">Affine</option>
            </select>
            <Button
              bsStyle="info"
              onClick={() => this.setState({ showPenaltyModal: true })}
            >
              Penalty Tips
            </Button>
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
            <div className="Affine-gap-input-div">
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
        {modalForInput}
        {modalForScoring}
        {modalForPenalty}
      </div>
    );
  }
}

export default AlignmentInputMenu;

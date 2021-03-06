import React, { Component } from "react";
import MatrixCell from "./MatrixCell";
import { Jumbotron } from "react-bootstrap";

class AlignedSequences extends Component {
  render() {
    if (
      this.props.alignedSequence1 === "undefined" ||
      this.props.alignedSequence1 === null ||
      this.props.alignedSequence1.length === 0 ||
      this.props.alignedSequence2 === "undefined" ||
      this.props.alignedSequence2 === null ||
      this.props.alignedSequence2.length === 0
    ) {
      return <div />;
    }
    return (
      <div>
        <Jumbotron>
          <h3>Optimal Alignment</h3>
          <div className="Aligned-sequences-table-div">
            <table>
              <tbody>
                <tr>
                  {this.props.alignedSequence1.map(function(char, index) {
                    return (
                      <td>
                        <MatrixCell highlight={false} value={char} />
                      </td>
                    );
                  })}
                </tr>
                <tr>
                  {this.props.alignedSequence2.map(function(char, index) {
                    return (
                      <td>
                        <MatrixCell highlight={false} value={char} />
                      </td>
                    );
                  })}
                </tr>
              </tbody>
            </table>
          </div>
        </Jumbotron>
      </div>
    );
  }
}

export default AlignedSequences;

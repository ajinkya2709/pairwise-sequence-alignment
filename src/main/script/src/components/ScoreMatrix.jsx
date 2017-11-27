import React, { Component } from "react";
import MatrixCell from "./MatrixCell";

class ScoreMatrix extends Component {
  render() {
    if (this.props.matrix === "undefined" || this.props.matrix.length === 0) {
      return <div />;
    }
    return (
      <div className="Score-matrix-div">
        <table className="Left-table">
          <tbody>
            <tr>
              <td>
                <MatrixCell highlight={false} value={"*"} />
              </td>
            </tr>
            <tr>
              <td>
                <MatrixCell highlight={false} value={"-"} />
              </td>
            </tr>
            {this.props.sequence1.map(function(char, index) {
              return (
                <tr key={index}>
                  <td>
                    <MatrixCell highlight={false} value={char} />
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
        <table className="Right-table">
          <tbody>
            <tr>
              <td>
                <MatrixCell highlight={false} value={"-"} />
              </td>
              {this.props.sequence2.map(function(char, index) {
                return (
                  <td>
                    <MatrixCell highlight={false} value={char} />
                  </td>
                );
              })}
            </tr>
            {this.props.matrix.map(function(row, rowIndex) {
              return (
                <tr key={rowIndex}>
                  {row.map(function(cell, columnIndex) {
                    return (
                      <td key={columnIndex}>
                        <MatrixCell
                          highlight={cell.shouldHighlight}
                          value={cell.value}
                        />
                      </td>
                    );
                  })}
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default ScoreMatrix;

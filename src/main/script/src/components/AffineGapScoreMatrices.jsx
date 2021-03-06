import React, { Component } from "react";
import ScoreMatrix from "./ScoreMatrix";

class AffineGapScoreMatrices extends Component {
  render() {
    // Migh not need the if case
    if (
      this.props.matrixM === "undefined" ||
      this.props.matrixX === "undefined" ||
      this.props.matrixY === "undefined"
    ) {
      return <div />;
    }
    return (
      <div>
        <div className="Score-matrixM-div">
          <ScoreMatrix
            title={"Score Matrix M"}
            matrix={this.props.matrixM}
            sequence1={this.props.sequence1}
            sequence2={this.props.sequence2}
          />
        </div>

        <div className="Score-matrixX-div">
          <ScoreMatrix
            title={"Score Matrix X"}
            matrix={this.props.matrixX}
            sequence1={this.props.sequence1}
            sequence2={this.props.sequence2}
          />
        </div>

        <div className="Score-matrixY-div">
          <ScoreMatrix
            title={"Score Matrix Y"}
            matrix={this.props.matrixY}
            sequence1={this.props.sequence1}
            sequence2={this.props.sequence2}
          />
        </div>
      </div>
    );
  }
}

export default AffineGapScoreMatrices;

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
      <div className="Affine-gap-score-matrices-div">
        <div className="Score-matrixM-div">
          <ScoreMatrix
            matrix={this.props.matrixM}
            sequence1={this.props.sequence1}
            sequence2={this.props.sequence2}
          />
        </div>

        <div className="Score-matrixX-div">
          <ScoreMatrix
            matrix={this.props.matrixX}
            sequence1={this.props.sequence1}
            sequence2={this.props.sequence2}
          />
        </div>

        <div className="Score-matrixY-div">
          <ScoreMatrix
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

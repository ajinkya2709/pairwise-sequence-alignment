import React, { Component } from "react";
import { Jumbotron } from "react-bootstrap";
import globalFormula from "../globalFormula.png";
import localFormula from "../localFormula.png";
import affineLocal from "../affineLocal.png";
import affineGlobal from "../affineGlobal.png";

class AlgoInfo extends Component {
  render() {
    let localAlignmentInfo = null;
    if (this.props.showLocalAlignmentInfo) {
      let formula = null;
      if (this.props.affineModelUsed) {
        formula = (
          <img
            src={affineLocal}
            alt={"Calculation Formula"}
            width={750}
            height={400}
          />
        );
      } else {
        formula = (
          <img
            src={localFormula}
            alt={"Calculation Formula"}
            width={300}
            height={100}
          />
        );
      }
      localAlignmentInfo = (
        <Jumbotron>
          <h3>Smith-Waterman Algorithm</h3>
          <p>
            The Smith-Waterman algorithm is a modification to Needleman-Wunsch
            to allow for local alignments (Smith and Waterman, 1981). The main
            difference to the Needleman–Wunsch algorithm is that negative
            scoring matrix cells are set to zero, which renders the (thus
            positively scoring) local alignments visible. Traceback procedure
            starts at the highest scoring matrix cell and proceeds until a cell
            with score zero is encountered, yielding the highest scoring local
            alignment.
          </p>
          <p>
            The entire algorithm is broken down into <b>2 steps</b>-
            <br />
            Filling the Score Matrix - Score matrix is filled using below
            formula
            <br />
            Tracing the Optimal Path - Backtracking from the highest score till
            we encounter a 0 in the Score matrix
            <br />
            <br />
            <h4>Score Matrix Formula</h4>
            {formula}
          </p>
        </Jumbotron>
      );
    }
    let globalAlignmentInfo = null;
    if (this.props.showGlobalAlignmentInfo) {
      let formula = null;
      if (this.props.affineModelUsed) {
        formula = (
          <img
            src={affineGlobal}
            alt={"Calculation Formula"}
            width={750}
            height={340}
          />
        );
      } else {
        formula = (
          <img
            src={globalFormula}
            alt={"Calculation Formula"}
            width={300}
            height={90}
          />
        );
      }
      globalAlignmentInfo = (
        <Jumbotron>
          <h3>Needleman-Wunsch Algorithm</h3>
          <p>
            The Needleman-Wunsch algorithm is a dynamic programming algorithm
            for optimal sequence alignment (Needleman and Wunsch, 1970).
            Basically, the concept behind the Needleman-Wunsch algorithm stems
            from the observation that any partial sub-path that tends at a point
            along the true optimal path must itself be the optimal path leading
            up to that point. Therefore the optimal path can be determined by
            incremental extension of the optimal sub-paths. In a NeedlemanWunsch
            alignment, the optimal path must stretch from beginning to end in
            both sequences (hence the term ‘global alignment’).{" "}
          </p>
          <p>
            The entire algorithm is broken down into <b>2 steps</b>-
            <br />
            Filling the Score Matrix - Score matrix is filled using below
            formula
            <br />
            Tracing the Optimal Path - Backtracking from the highest score till
            we encounter the start of both sequences
            <br />
            <br />
            <h4>Score Matrix Formula</h4>
            {formula}
          </p>
        </Jumbotron>
      );
    }
    return (
      <div>
        {localAlignmentInfo}
        {globalAlignmentInfo}
      </div>
    );
  }
}

export default AlgoInfo;

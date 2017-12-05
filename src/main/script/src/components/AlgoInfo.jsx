import React, { Component } from "react";
import { Jumbotron } from "react-bootstrap";

class AlgoInfo extends Component {
  render() {
    let localAlignmentInfo = null;
    if (this.props.showLocalAlignmentInfo) {
      localAlignmentInfo = (
        <Jumbotron>
          <h3>Smith-Waterman Algorithm</h3>
          <p>
            The Smith-Waterman algorithm is a modification to Needleman-Wunsch
            to allow for local alignments (Smith and Waterman, 1981). In this
            adaptation, the alignment path does not need to reach the edges of
            the search graph, but may begin and end internally. In order to
            accomplish this, 0 was added as a term in the score calculation
            described by Needleman and Wunsch.
          </p>
        </Jumbotron>
      );
    }
    let globalAlignmentInfo = null;
    if (this.props.showGlobalAlignmentInfo) {
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
            both sequences (hence the term ‘global alignment’).
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

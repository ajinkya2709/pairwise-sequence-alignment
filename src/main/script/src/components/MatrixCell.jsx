import React, { Component } from "react";

class MatrixCell extends Component {
  constructor(props) {
    super(props);
    let bgcolor = "#FFF";
    if (this.props.highlight) {
      bgcolor = "#ADFF2F";
    }
    this.state = {
      backgroundColor: bgcolor
    };
  }

  render() {
    return (
      <div className="Cell" style={this.state}>
        {this.props.value}
      </div>
    );
  }
}

export default MatrixCell;

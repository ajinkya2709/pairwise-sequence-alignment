import React, { Component } from "react";

class MatrixCell extends Component {
  constructor(props) {
    super(props);
    let bgcolor = "#FFF";
    let fontWeight = "normal";
    if (this.props.highlight) {
      bgcolor = "#ADFF2F";
    }
    if (this.props.bold) {
      fontWeight = "bold";
    }
    this.state = {
      backgroundColor: bgcolor,
      fontWeight: fontWeight
    };
  }

  componentWillReceiveProps(nextProps) {
    let bgcolor = "#FFF";
    let fontWeight = "normal";
    if (nextProps.highlight) {
      bgcolor = "#ADFF2F";
    }
    if (nextProps.bold) {
      fontWeight = "bold";
    }
    this.setState({
      backgroundColor: bgcolor,
      fontWeight: fontWeight
    });
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

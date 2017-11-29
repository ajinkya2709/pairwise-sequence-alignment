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

  componentWillReceiveProps(nextProps) {
    let bgcolor = "#FFF";
    if (nextProps.highlight) {
      bgcolor = "#ADFF2F";
    }
    this.setState({
      backgroundColor: bgcolor
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

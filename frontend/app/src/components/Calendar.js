import React, {Component} from "react";
import {DayPilot, DayPilotMonth, DayPilotCalendar} from "@daypilot/daypilot-lite-react";
import RequestService from "../serviceHubs/RequestServiceHub";

class Calendar extends Component {

    constructor(props) {
        super(props);
        this.calendarRef = React.createRef();

        this.state = {
            startDate: DayPilot.Date.today()
        }
    }

    componentDidMount() {

        // load using remote HTTP endpoint.
        this.loadEvents();
    }

    async loadEvents() {
        const start = this.calendar.visibleStart();
        const end = this.calendar.visibleEnd();
        const {data} = await DayPilot.Http.get("/api/v1/requests");
        this.calendar.update({events: data});
        console.log(data)

        // load using direct API
        // const events = [
        //     {
        //         id: 1,
        //         text: "Event 1",
        //         start: "2023-02-01",
        //         end: "2023-02-09"
        //     },
        //     {
        //         id: 2,
        //         text: "Event 2",
        //         start: "2023-02-09",
        //         end: "2023-02-10"
        //     },
        // ];
        // this.calendar.update({events});

        // load event data
        // this.setState({
        //     events: [
        //         {
        //             id: 1,
        //             text: "Event 1",
        //             start: "2023-02-02",
        //             end: "2023-02-03"
        //         },
        //         {
        //             id: 2,
        //             text: "Event 2",
        //             start: "2023-02-02",
        //             end: "2023-02-03"
        //         },
        //         {
        //             id: 3,
        //             text: "Event 3",
        //             start: "2023-02-02",
        //             end: "2023-02-03"
        //         },
        //         {
        //             id: 4,
        //             text: "Event 4",
        //             start: "2023-02-02",
        //             end: "2023-02-03"
        //         },
        //         {
        //             id: 5,
        //             text: "Event 5",
        //             start: "2023-02-02",
        //             end: "2023-02-03"
        //         },
        //     ]
        // })
    }

    get calendar() {
        return this.calendarRef.current.control;
    }

    render() {
        return (
            <div className='calendar'>
                <DayPilotMonth
                    {...this.state}
                    ref={this.calendarRef}
                />
            </div>

            // <DayPilotCalendar
            //     viewType={"Week"}
            //     onTimeRangeSelected={args => {
            //         this.calendar.message("Selected range: " + args.start.toString("hh:mm tt") + " - " + args.end.toString("hh:mm tt"));
            //     }}
            //     ref={this.calendarRef}
            // />
        );
    }
}

export default Calendar
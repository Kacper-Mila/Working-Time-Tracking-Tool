import React, {Component} from "react";
import {DayPilot, DayPilotMonth, DayPilotCalendar} from "daypilot-pro-react";
import RequestService from "../../serviceHubs/request-service-hub";

const deleteRequest = async (id) => {
    await RequestService.deleteRequest(id);
    await requestArrayBuilder();
}

const requestArrayBuilder = async () => {
    let data = await RequestService.getRequestsByUserId(localStorage.getItem("userId"));
    return data.map(val => ({
        id: val.id,
        text: val.type,
        start: val.startDate + "T00:00:00",
        end: incrementDate(val.endDate)
    }))
}

// TODO quickfix for request end date display
const incrementDate = (endDate) => {
    let date = new Date(endDate)
    return addOneDay(date)
}

function addOneDay(date) {
    date.setDate(date.getDate() + 1);
    return date;
}

class Calendar extends Component {

    constructor(props) {
        super(props);
        this.calendarRef = React.createRef();
        this.state = {
            locale: "en-us",
            viewType: "Month",
            showWeekend: true,
            timeRangeSelectedHandling: "Enabled",
            onTimeRangeSelected: async (args) => {
                const modal = await DayPilot.Modal.prompt("Create a new request:", "Request type:");
                const dp = args.control;
                dp.clearSelection();
                if (modal.canceled) {
                    return;
                }
                dp.events.add({
                    start: args.start,
                    end: args.end,
                    id: DayPilot.guid(),
                    text: modal.result
                });
            },
            eventDeleteHandling: "Update",
            onEventDeleted: (args) => {
                deleteRequest(args.e.id())
                args.control.message("Event deleted: " + args.e.text());
            },
            // eventMoveHandling: "Update",
            // onEventMoved: (args) => {
            //     args.control.message("Event moved: " + args.e.text());
            // },
            // eventResizeHandling: "Update",
            // onEventResized: (args) => {
            //     args.control.message("Event resized: " + args.e.text());
            // },
            eventClickHandling: "Enabled",
            onEventClicked: (args) => {
                args.control.message("Event clicked: " + args.e.text());
            },
            eventHoverHandling: "Bubble",
            bubble: new DayPilot.Bubble({
                onLoad: (args) => {
                    // if event object doesn't specify "bubbleHtml" property
                    // this onLoad handler will be called to provide the bubble HTML
                    args.html = "Event details";
                }
            }),
            contextMenu: new DayPilot.Menu({
                items: [
                    {
                        text: "Delete", onClick: (args) => {
                            deleteRequest(args.source.id())
                            const dp = args.source.calendar;
                            dp.events.remove(args.source);
                        }
                    }
                ]
            }),
        };
    }

    componentDidMount() {
        requestArrayBuilder()
            .then(d => {
                this.setState({
                    startDate: DayPilot.Date.today(),
                    events: d
                })
            })
    }

    get cal() {
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
        );
    }
}

export default Calendar
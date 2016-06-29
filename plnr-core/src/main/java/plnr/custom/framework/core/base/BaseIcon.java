package plnr.custom.framework.core.base;

import plnr.custom.framework.R;

/**
 * Created by nattapongr on 4/28/15.
 */
public class BaseIcon {
    static int chooseIcon;

    public static int getIcon(String id) {
        switch (id) {
            case "cal_status_accept":
                chooseIcon = R.drawable.cal_status_accept;
                break;
            case "cal_status_assign":
                chooseIcon = R.drawable.cal_status_assign;
                break;
            case "cal_status_finish":
                chooseIcon = R.drawable.cal_status_finish;
                break;
            case "cal_status_progress":
                chooseIcon = R.drawable.cal_status_progress;
                break;
            case "cal_status_reject":
                chooseIcon = R.drawable.cal_status_reject;
                break;
            case "cal_status_send":
                chooseIcon = R.drawable.cal_status_send;
                break;
            case "cal_status_accept_dl":
                chooseIcon = R.drawable.cal_status_accept_dl;
                break;
            case "cal_status_finish_dl":
                chooseIcon = R.drawable.cal_status_finish_dl;
                break;
            case "cal_status_progress_dl":
                chooseIcon = R.drawable.cal_status_progress_dl;
                break;
            case "cal_status_reject_dl":
                chooseIcon = R.drawable.cal_status_reject_dl;
                break;
            case "cal_status_send_dl":
                chooseIcon = R.drawable.cal_status_send_dl;
                break;
            case "ic_status_assign":
                chooseIcon = R.drawable.ic_status_assign;
                break;
            case "ic_status_accept":
                chooseIcon = R.drawable.ic_status_accept;
                break;
            case "ic_status_progress":
                chooseIcon = R.drawable.ic_status_progress;
                break;
            case "ic_status_send":
                chooseIcon = R.drawable.ic_status_send;
                break;
            case "ic_status_reject":
                chooseIcon = R.drawable.ic_status_reject;
                break;
            case "ic_status_finish":
                chooseIcon = R.drawable.ic_status_finish;
                break;
            case "ic_qstatus_wait": //QT wait
                chooseIcon = R.drawable.ic_qstatus_wait;
                break;
            case "ic_qstatus_finish": //QT finish
                chooseIcon = R.drawable.ic_qstatus_finish;
                break;
            case "ic_qstatus_reject": //QT Reject
                chooseIcon = R.drawable.ic_qstatus_reject;
                break;
            case "ic_req_activity": //RRM assignment
                chooseIcon = R.drawable.ic_req_activity;
                break;
            case "ic_req_quotation": //RRM quotation
                chooseIcon = R.drawable.ic_req_quotation;
                break;
            case "ic_activity_active":
                chooseIcon = R.drawable.ic_activity_active;
                break;
            case "ic_activity":
                chooseIcon = R.drawable.ic_activity;
                break;
            case "ic_assign_active":
                chooseIcon = R.drawable.ic_assign_active;
                break;
            case "ic_assign":
                chooseIcon = R.drawable.ic_assign;
                break;
            case "ic_calendar_active":
                chooseIcon = R.drawable.ic_calendar_active;
                break;
            case "ic_calendar":
                chooseIcon = R.drawable.ic_calendar;
                break;
            case "ic_contact_active":
                chooseIcon = R.drawable.ic_contact_active;
                break;
            case "ic_contact":
                chooseIcon = R.drawable.ic_contact;
                break;
            case "ic_empty_conlist":
                chooseIcon = R.drawable.ic_empty_conlist;
                break;
            case "ic_empty_quotation":
                chooseIcon = R.drawable.ic_empty_quotation;
                break;
            case "ic_feed_active":
                chooseIcon = R.drawable.ic_feed_active;
                break;
            case "ic_feed":
                chooseIcon = R.drawable.ic_feed;
                break;
            case "ic_notification_active":
                chooseIcon = R.drawable.ic_notification_active;
                break;
            case "ic_notification":
                chooseIcon = R.drawable.ic_notification;
                break;
            case "ic_office_active":
                chooseIcon = R.drawable.ic_office_active;
                break;
            case "ic_office":
                chooseIcon = R.drawable.ic_office;
                break;
            case "ic_onsite_active":
                chooseIcon = R.drawable.ic_onsite_active;
                break;
            case "ic_onsite":
                chooseIcon = R.drawable.ic_onsite;
                break;
            case "ic_quotation_active":
                chooseIcon = R.drawable.ic_quotation_active;
                break;
            case "ic_quotation":
                chooseIcon = R.drawable.ic_quotation;
                break;
            case "ic_report_active":
                chooseIcon = R.drawable.ic_report_active;
                break;
            case "ic_report":
                chooseIcon = R.drawable.ic_report;
                break;
            case "ic_settings_active":
                chooseIcon = R.drawable.ic_settings_active;
                break;
            case "ic_settings":
                chooseIcon = R.drawable.ic_settings;
                break;
            case "ic_hm_question":
                chooseIcon = R.drawable.ic_hm_question;
                break;
            case "ic_transac":
                chooseIcon = R.drawable.ic_transac;
                break;
            case "ic_transac_item":
                chooseIcon = R.drawable.ic_transac_item;
                break;
            case "ic_transac_all":
                chooseIcon = R.drawable.ic_transac_all;
                break;
            case "work_appear":
                chooseIcon = R.drawable.work_appear;
                break;
            case "work_expect":
                chooseIcon = R.drawable.work_expect;
                break;
            case "work_location":
                chooseIcon = R.drawable.work_location;
                break;
            case "work_phone":
                chooseIcon = R.drawable.work_phone;
                break;
            case "work_problem":
                chooseIcon = R.drawable.work_problem;
                break;
            case "work_receive":
                chooseIcon = R.drawable.work_receive;
                break;
            case "work_remark":
                chooseIcon = R.drawable.work_remark;
                break;
            case "work_serial":
                chooseIcon = R.drawable.work_serial;
                break;
            case "ic_chevron_right":
                chooseIcon = R.drawable.ic_chevron_right;
                break;
            case "ic_onprogress":
                chooseIcon = R.drawable.ic_onprogress;
                break;
            case "ic_onprogress_active":
                chooseIcon = R.drawable.ic_onprogress_active;
                break;
            case "ic_schedule":
                chooseIcon = R.drawable.ic_schedule;
                break;
            case "ic_schedule_active":
                chooseIcon = R.drawable.ic_schedule_active;
                break;
            case "ic_setting":
                chooseIcon = R.drawable.ic_setting;
                break;
            case "ic_setting_active":
                chooseIcon = R.drawable.ic_setting_active;
                break;
            case "ic_transaction":
                chooseIcon = R.drawable.ic_transaction;
                break;
            case "ic_transaction_active":
                chooseIcon = R.drawable.ic_transaction_active;
                break;
            case "ic_worklist":
                chooseIcon = R.drawable.ic_worklist;
                break;
            case "ic_worklist_active":
                chooseIcon = R.drawable.ic_worklist_active;
                break;
            default:
                chooseIcon = R.drawable.ic_status_default;
                break;
        }
        return chooseIcon;
    }

    public static int getIcon(int id) {
        switch (id) {
            case 0:
                chooseIcon = R.drawable.ic_status_assign;
                break;
            case 1:
                chooseIcon = R.drawable.ic_status_accept;
                break;
            case 2:
                chooseIcon = R.drawable.ic_status_progress;
                break;
            case 3:
                chooseIcon = R.drawable.ic_status_progress;
                break;
            case 4:
                chooseIcon = R.drawable.ic_status_send;
                break;
            case 5:
                chooseIcon = R.drawable.ic_status_reject;
                break;
            case 6: //QT wait
                chooseIcon = R.drawable.ic_qstatus_wait;
                break;
            case 7: //QT finish
                chooseIcon = R.drawable.ic_qstatus_finish;
                break;
            case 8: //QT Reject
                chooseIcon = R.drawable.ic_qstatus_reject;
                break;
            case 9: //RRM assignment
                chooseIcon = R.drawable.ic_req_activity;
                break;
            case 10: //RRM quotation
                chooseIcon = R.drawable.ic_req_quotation;
                break;
            default:
                chooseIcon = R.drawable.ic_status_default;
                break;
        }
        return chooseIcon;
    }

    public static int getHomeIcon(String moduleName) {
        switch (moduleName) {
            case "RRM":
                chooseIcon = R.drawable.ic_hm_notification;
                break;
            case "AFM":
                chooseIcon = R.drawable.ic_hm_feed;
                break;
            case "OSM":
                chooseIcon = R.drawable.ic_hm_onsite;
                break;
            case "OFM":
                chooseIcon = R.drawable.ic_hm_office;
                break;
            case "PAM":
                chooseIcon = R.drawable.ic_hm_assign;
                break;
            case "CDM":
                chooseIcon = R.drawable.ic_hm_calendar;
                break;
            case "MAM":
                chooseIcon = R.drawable.ic_hm_activity;
                break;
            case "CLM":
                chooseIcon = R.drawable.ic_hm_contact;
                break;
            case "QTM":
                chooseIcon = R.drawable.ic_hm_quotation;
                break;
            case "RPM":
                chooseIcon = R.drawable.ic_hm_report;
                break;
            case "TTM":
                chooseIcon = R.drawable.ic_hm_transac;
                break;
            case "WLM":
                chooseIcon = R.drawable.ic_hm_worklist;
                break;
            case "OPM":
                chooseIcon = R.drawable.ic_hm_onprogress;
                break;
            case "WTM":
                chooseIcon = R.drawable.ic_hm_schedule;
                break;
            default:
                chooseIcon = R.drawable.ic_status_default;
        }
        return chooseIcon;
    }
}

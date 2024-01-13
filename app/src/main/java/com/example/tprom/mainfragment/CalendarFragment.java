package com.example.tprom.mainfragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tprom.MainActivity;
import com.example.tprom.R;
import com.example.tprom.group.GroupItem;
import com.example.tprom.group.adapters.TaskAdapter;
import com.example.tprom.group.mainfragment.GroupDetailsFragment;
import com.example.tprom.properties.Member;
import com.example.tprom.properties.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Task> tasks;
    TextView tv_groupName, tv_description;
    boolean isAdmin;
    private View view;
    private TextView[] dayTextViews = new TextView[7];
    private Date[][] weeks;
    private int currentWeekIndex;
    private TextView lastClickedTextView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_description = view.findViewById(R.id.tv_description);
        tv_groupName = view.findViewById(R.id.tv_groupName);

        recyclerView = view.findViewById(R.id.calendar_rv);
        tasks = new ArrayList<>();
        TaskAdapter taskAdapter = new TaskAdapter(this.getContext(), tasks, isAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();

        if (getActivity() != null) {
            MainActivity mainActivity = (MainActivity) getActivity();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String uid = user.getUid();
                DatabaseReference getUser = FirebaseDatabase.getInstance().getReference("users").child(uid).child("username");
                getUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    String username;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            username = dataSnapshot.getValue(String.class);
                        }
                        DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups");
                        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            ArrayList<Member> members = new ArrayList<>();
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                                    GroupItem memberName = memberSnapshot.getValue(GroupItem.class);

                                    members = memberName.getMembers();
                                    for (Member member : members) {
                                        String name = member.getName();
                                        if (name.equals(username) && member.getRole().equals("leader")) {
                                            isAdmin = true;
                                            break;
                                        }
                                    }
                                    break;
                                }
                                DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks");
                                taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        tasks.clear();
                                        for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                                            Task task = taskSnapshot.getValue(Task.class);
                                            if (task != null) {
                                                String nametask = task.getTaskName();
                                                String description = task.getTaskDescription();
                                                ArrayList<Member> members = task.getAssignedUsers();
                                                int numberOfFiles = task.getFiles().size();
                                                String startTime = task.getTaskStartTime();
                                                String dueTime = task.getTaskDueTime();
                                                int statusTask = task.getStatus();
                                                double progressPercent = task.getProgressPercent();
                                                boolean isCurrentUserAssigned = false;
                                                for (Member member : members) {
                                                    String name = member.getName();
                                                    if (name.equals(username)) {
                                                        isCurrentUserAssigned = true;
                                                        break;
                                                    }
                                                }
                                                if (isCurrentUserAssigned) {
                                                    tasks.add(new Task(nametask, description, numberOfFiles,startTime, dueTime));
                                                }
                                            }
                                        }
//                                        //TaskAdapter taskAdapter = new TaskAdapter(getContext(), tasks, finalIsAdmin);
//                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//                                        recyclerView.setAdapter(taskAdapter);
//                                        // Gọi notifyDataSetChanged() ở đây để cập nhật RecyclerView
//                                        taskAdapter.notifyDataSetChanged();

                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        // Xử lý khi có lỗi
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        dayTextViews[0] = view.findViewById(R.id.calendar_day_1);
        dayTextViews[1] = view.findViewById(R.id.calendar_day_2);
        dayTextViews[2] = view.findViewById(R.id.calendar_day_3);
        dayTextViews[3] = view.findViewById(R.id.calendar_day_4);
        dayTextViews[4] = view.findViewById(R.id.calendar_day_5);
        dayTextViews[5] = view.findViewById(R.id.calendar_day_6);
        dayTextViews[6] = view.findViewById(R.id.calendar_day_7);

        weeks = new Date[100][7];
        currentWeekIndex = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < weeks.length; i++) {
            for (int j = 0; j < 7; j++) {
                weeks[i][j] = calendar.getTime();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                dayTextViews[j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleTextViewClick((TextView) v);
                        v.setBackgroundColor(getResources().getColor(R.color.blue2));
                        if (lastClickedTextView != null && lastClickedTextView != v) {
                            lastClickedTextView.setBackgroundColor(Color.TRANSPARENT); // hoặc màu nền mặc định
                        }
                        lastClickedTextView = (TextView) v;
                    }
                });
            }
        }
        updateCurrentWeekText();
        TextView previousWeekTextView = view.findViewById(R.id.calendar_btn_previous);
        previousWeekTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWeekIndex > 0 && isNotFirstWeekOfTheYear()) {
                    currentWeekIndex--;
                    updateCurrentWeekText();
                }
            }
        });
        TextView nextWeekTextView = view.findViewById(R.id.calendar_btn_next);
        nextWeekTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWeekIndex < weeks.length - 1) {
                    currentWeekIndex++;
                    updateCurrentWeekText();
                }
            }
        });
    }
    private boolean isNotFirstWeekOfTheYear() {
        // Kiểm tra xem tuần hiện tại có phải là tuần đầu tiên của năm không
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        currentCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 0);

        Calendar firstWeekOfYear = Calendar.getInstance();
        firstWeekOfYear.setFirstDayOfWeek(Calendar.MONDAY);
        firstWeekOfYear.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        firstWeekOfYear.set(Calendar.HOUR_OF_DAY, 0);
        firstWeekOfYear.set(Calendar.MINUTE, 0);
        firstWeekOfYear.set(Calendar.SECOND, 0);
        firstWeekOfYear.set(Calendar.MILLISECOND, 0);
        firstWeekOfYear.set(Calendar.DAY_OF_YEAR, 1);
        return !currentCalendar.equals(firstWeekOfYear);
    }
    private void updateCurrentWeekText() {
        Date[] currentWeek = weeks[currentWeekIndex];
        for (int i = 0; i < 7; i++) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
            String dayOfWeek = dayFormat.format(currentWeek[i]);
            String dayOfMonth = dateFormat.format(currentWeek[i]);
            dayTextViews[i].setText(dayOfWeek + "\n" + dayOfMonth);

            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentWeek[i]);
            currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentCalendar.set(Calendar.MINUTE, 0);
            currentCalendar.set(Calendar.SECOND, 0);
            currentCalendar.set(Calendar.MILLISECOND, 0);

            Calendar todayCalendar = Calendar.getInstance();
            todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
            todayCalendar.set(Calendar.MINUTE, 0);
            todayCalendar.set(Calendar.SECOND, 0);
            todayCalendar.set(Calendar.MILLISECOND, 0);

            if (currentCalendar.getTime().equals(todayCalendar.getTime())) {
                dayTextViews[i].setBackgroundResource(R.drawable.highlight_background);
            } else {
                dayTextViews[i].setBackgroundResource(0);
            }
        }
    }
    private void handleTextViewClick(TextView clickedTextView) {
        String dayText = clickedTextView.getText().toString().split("\n")[1];
        int clickedDay = Integer.parseInt(dayText);
        List<Task> tasksToShow = new ArrayList<>();
        for (Task task : tasks) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            try {
                Date startTime = dateFormat.parse(task.getTaskStartTime());
                Date dueTime = dateFormat.parse(task.getTaskDueTime());

                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(startTime);
                int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);

                Calendar dueCalendar = Calendar.getInstance();
                dueCalendar.setTime(dueTime);
                int dueDay = dueCalendar.get(Calendar.DAY_OF_MONTH);
                    if (dueDay >= clickedDay && clickedDay >= startDay) {
                        tasksToShow.add(task);
                    }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for (Task task : tasksToShow) {
            Log.d("TaskToShow", "Task: " + task.getTaskName());
        }
        TaskAdapter taskAdapter = new TaskAdapter(getContext(), (ArrayList<Task>) tasksToShow, isAdmin);
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }
}




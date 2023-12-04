package com.example.billetblaze.ui.commsHub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.billetblaze.R;
import com.example.billetblaze.ui.messages.Contact;
import com.example.billetblaze.ui.messages.Message;
import com.example.billetblaze.ui.messages.MyAdapterContacts;
import com.example.billetblaze.ui.messages.MyAdapterMessages;

import java.util.ArrayList;
import java.util.List;

public class messageFragment extends Fragment {

    View root;
    List<String> convo;

    List<Message> messages;

    Button enterbutton;
    EditText messageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_messages, container, false);

        enterbutton = root.findViewById(R.id.enterButton);
        messageView = root.findViewById(R.id.messageText);

        Bundle bundle = getArguments();

        Contact contact = bundle.getParcelable("object");

        Toast.makeText(root.getContext(), contact.getName(), Toast.LENGTH_SHORT);

        convo = new ArrayList<String>();
        convo = contact.getConvo();
        messages = new ArrayList<Message>();
        for(int i = 0; i < convo.size(); i++){
            if(convo.get(i).startsWith("host:")){
                messages.add(new Message("Host", convo.get(i).substring(5)));
            }else if(convo.get(i).startsWith("user:")){
                messages.add(new Message("Bruce Wayne", convo.get(i).substring(5)));
            }

        }


        setRecyclerView();
        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageView.getText().toString();
                messages.add(new Message("Bruce Wayne", message));
                messageView.setText("");
                //setRecyclerView();
                runSimulation(message);
            }
        });


        // Inflate the layout for this fragment
        return root;
    }

    public void runSimulation(String response){
        if(response.equals("thank you!")){
            String message = "You're welcome!";
            messages.add(new Message("Host", message));
        }
    }

    public void setRecyclerView(){
        RecyclerView recyclerView = root.findViewById(R.id.messagerecyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 1));
        recyclerView.setAdapter(new MyAdapterMessages(root.getContext().getApplicationContext(), messages));
    }

}
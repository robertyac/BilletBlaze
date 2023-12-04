package com.example.billetblaze.ui.commsHub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.billetblaze.R;
import com.example.billetblaze.ui.messages.Contact;
import com.example.billetblaze.ui.messages.MyAdapterContacts;
import com.example.billetblaze.ui.messages.SelectListener;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment implements SelectListener {

    //private String selectedItem;
    private List<Contact> contacts;
    private View root;
    private Contact baneContact;
    private Contact bruceContact;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        root = inflater.inflate(R.layout.fragment_contacts, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.contactrecyclerview);


        contacts = new ArrayList<Contact>();

        List<String> bruceConvo = new ArrayList<String>();
        bruceConvo.add("host:Hi Bruce, thank you for your booking!");
        bruceConvo.add("user:Hello Host! Thanks for having me!");
        bruceConvo.add("host:Enjoy your stay, message me if you need anything");
        bruceContact = new Contact("Bruce Wayne", 1, bruceConvo);
        contacts.add(bruceContact);

        List<String> baneConvo = new ArrayList<String>();
        baneConvo.add("host:Hi Bruce, thank you for your booking!");
        baneConvo.add("user:Hello Host! Thanks for having me!");
        bruceConvo.add("host:Enjoy your stay, message me if you need anything");
        bruceConvo.add("user:The fire extinguisher is broken");
        bruceConvo.add("host:I'll replace it immediately!");
        baneContact = new Contact("Joe Mann", 1, baneConvo);
        contacts.add(baneContact);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 1));
        recyclerView.setAdapter(new MyAdapterContacts(root.getContext().getApplicationContext(), contacts, this));



        return root;
    }

    @Override
    public void onItemClicked(Contact contact) {
        Toast.makeText(root.getContext(), contact.getName(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        if(contact.getName().equals("Bruce Wayne")){
            bundle.putParcelable("object", bruceContact);
        }

        Navigation.findNavController(root).navigate(R.id.action_navigation_Messages_to_messagesList, bundle);

    }
}
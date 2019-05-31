package philosophicas.org.avgasyv

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList


class AirportsStatusActivity : AppCompatActivity() {


    private lateinit var airportsStatusRecyclerView : RecyclerView



    class AirportsStatusAdapter(
        var airportsStatus : ArrayList<AirportInfo>,
        var comments: ArrayList<Comment>
    ) :
        RecyclerView.Adapter<AirportsStatusAdapter.AirportsStatusViewHolder>() {

        private lateinit var designator: TextView
        private lateinit var light : IndicatorLight
        private lateinit var commentsRecyclerView: RecyclerView


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportsStatusViewHolder {
            val view =  LayoutInflater.from(parent.context)
                .inflate(R.layout.airports_recycler_view_row, parent, false) as ViewGroup

            designator = view.findViewById(R.id.airports_status_row_designator)
            light = view.findViewById(R.id.airports_status_row_fuel_indicator_light)
            commentsRecyclerView = view.findViewById(R.id.comments_recycler_view)

            commentsRecyclerView.layoutManager = LinearLayoutManager(parent.context)


            view.setOnClickListener {
                var rv = it.findViewById<RecyclerView>(R.id.comments_recycler_view)
                var ds = it.findViewById<TextView>(R.id.airports_status_row_designator)
                if (rv.visibility == View.VISIBLE ) {
                    rv.visibility = View.GONE
                } else {
                    rv.visibility = View.VISIBLE

                    val com = comments.filter {
                        return@filter it.designator == ds.text.toString()
                    }.toTypedArray()

                    commentsRecyclerView.adapter = CommentsAdapter(com)
                    commentsRecyclerView.layoutManager = LinearLayoutManager(it.context)
                }
            }

            return  AirportsStatusViewHolder(view)
        }

        override fun getItemCount(): Int {
            return  airportsStatus.size
        }

        override fun onBindViewHolder(holder: AirportsStatusViewHolder, position: Int) {
            designator.text = airportsStatus[position].designator
            light.status = IndicatorLight.IndicatorStatus.values()[airportsStatus[position].status]

        }

        class AirportsStatusViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    }



    class CommentsAdapter (var comments: Array<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

        private lateinit var userName : TextView
        private lateinit var date : TextView
        private lateinit var comment : TextView


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
            val view =   LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_recycler_view_row, parent, false) as ViewGroup

            userName = view.findViewById(R.id.comment_user_name)
            date = view.findViewById(R.id.comment_date)
            comment = view.findViewById(R.id.comment_text)

            return CommentsViewHolder(view)
        }

        override fun getItemCount(): Int {
            return comments.size
        }

        override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
            userName.text = comments[position].comment
            date.text = comments[position].date.toString()
            comment.text = comments[position].comment
        }

        class CommentsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder (view)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airports_status)

        var airports = ArrayList<AirportInfo>()
        var comments = ArrayList<Comment>()


        airports.add(AirportInfo().apply { designator = "SVCS"; status = 0 })
        airports.add(AirportInfo().apply { designator = "SVBC"; status = 1 })
        airports.add(AirportInfo().apply { designator = "SVMG"; status = 2 })
        airports.add(AirportInfo().apply { designator = "SVVP"; status = 2 })

        comments.add(Comment().apply {
            designator = "SVCS"
            date = Date(2019,5,22)
            userName = "Aldo"
            comment = "Hola mundo"
        })


        airportsStatusRecyclerView = findViewById(R.id.airports_status_recycler_view)
        airportsStatusRecyclerView.adapter = AirportsStatusAdapter(airports, comments)
        airportsStatusRecyclerView.layoutManager = LinearLayoutManager(this)

    }



}
